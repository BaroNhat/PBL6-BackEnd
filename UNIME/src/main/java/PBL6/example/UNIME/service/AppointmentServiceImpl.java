package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.AppointmentCreateRequest;
import PBL6.example.UNIME.dto.request.AppointmentUpdateRequest;
import PBL6.example.UNIME.dto.response.AppointmentReponse;
import PBL6.example.UNIME.entity.*;
import PBL6.example.UNIME.entity.DoctorService;
import PBL6.example.UNIME.enums.AppointmentStatus;
import PBL6.example.UNIME.enums.DoctorTimeworkStatus;
import PBL6.example.UNIME.exception.AppException;
import PBL6.example.UNIME.exception.ErrorCode;
import PBL6.example.UNIME.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class AppointmentServiceImpl implements AppointmentService{
    Locale vietnam = new Locale("vi", "VN");
    DateTimeFormatter FORMATTIME = DateTimeFormatter.ofPattern("HH:mm");
    PatientRepository patientRepository;
    DoctorTimeworkRepository doctorTimeworkRepository;
    DoctorServiceRepository doctorServiceRepository;
    AppointmentRepository appointmentRepository;
    DoctorTimeworkServiceImpl doctorTimeworkService;
    EmployeeRepository employeeRepository;
    AppointmentHistoryService appointmentHistoryService;
    MailService mailService;


    public AppointmentReponse createAppointment(String username, AppointmentCreateRequest request) {
        Appointment appointment = new Appointment();

        Patient patient = patientRepository.findBypatientUserUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.PATIENT_NOT_FOUND));
        appointment.setPatient(patient);

        DoctorTimework dtimework = doctorTimeworkRepository.findById(request.getDoctortimeworkId())
                .orElseThrow(() -> new AppException(ErrorCode.DOCTORTIMEWORK_NOT_FOUND));
        appointment.setDoctortimework(dtimework);

        DoctorService ds = doctorServiceRepository.findByDoctorAndService(request.getDoctorId(), request.getServiceId());
        if(ds==null) throw new AppException(ErrorCode.DOCTORSERVICE_NOT_FOUND);
        appointment.setDoctorservice(ds);

        appointmentRepository.save(appointment);
        mailService.sendSuccessEmail(appointment);
        dtimework.setStatus(DoctorTimeworkStatus.Busy.name());
        log.info("3: {}", dtimework.getStatus());
        doctorTimeworkService.updateDoctorTimework(dtimework.getId(), dtimework.getStatus());

        return mapToAppointmentResponse(appointment);
    }

    public String updateByDoctor( AppointmentUpdateRequest request) {

        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_FOUND));

        appointment.setAppointmentStatus(AppointmentStatus.Completed.name());

        // theem vao history và xóa record hiện tại
        appointmentHistoryService.addAppointment(appointment);
        appointmentRepository.delete(appointment);

        return "Thanh cong";
    }


    public String updateByEmployee(String username, AppointmentUpdateRequest request) {
        Employee employee = employeeRepository.findByemployeeUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));
        log.info("11");
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_FOUND));
        log.info("22");
        appointment.setAppointmentStatus(AppointmentStatus.Cancelled.name());
        String note = "Bệnh viện đã hủy lịch ";
        if (request.getAppointmentNote() != null) {
            note +="vì lí do: " + request.getAppointmentNote();
        }
        appointment.setAppointmentNote(note);
        appointment.setEmployee(employee);

//         theem vao history và xóa record hiện tại
        appointmentHistoryService.addAppointment(appointment);
        appointmentRepository.delete(appointment);
        /// Thiếu nhá ^-^
        // goọi mail thông báo hủy cho doc và patien
        mailService.sendCancelEmail(appointment);

        DoctorTimework dt = appointment.getDoctortimework();
        dt.setStatus(DoctorTimeworkStatus.Available.name());
        doctorTimeworkRepository.save(dt);

        return "Thanh cong";
    }


    @Scheduled(fixedRate = 1800000)
    public void autoCancelAppointment() {
        List<Appointment> allAppointments = appointmentRepository.findAll();
        log.info("allAppointments: {}", allAppointments.size());
        int i = 0;
        for (Appointment appointment : allAppointments) {
            if(isBeforeToday(appointment)){
                log.info("i: "+ ++i);
                appointment.setAppointmentStatus(AppointmentStatus.Cancelled.name());
                appointment.setAppointmentNote("Bệnh viện đã hủy lịch vì lí do: Bệnh nhân đã không đến đúng lịch đã đặt");
                appointmentHistoryService.addAppointment(appointment);
                appointmentRepository.delete(appointment);
            }
        }
    }

    private boolean isBeforeToday(Appointment appointment) {
        int year = appointment.getDoctortimework().getYear();
        int week = appointment.getDoctortimework().getWeekOfYear();

        String dayOfWeekStr = appointment.getDoctortimework().getTimeWork().getDayOfWeek();
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(dayOfWeekStr);
        LocalTime endTime = appointment.getDoctortimework().getTimeWork().getEndTime();

        LocalDate date = getDateFromWeekAndDayOfWeek(week, year, dayOfWeek);
        if(date.isBefore(LocalDate.now())) return true;
        else if(date.equals(LocalDate.now()) && endTime.isBefore(LocalTime.now())) return true;
        else return false;
    }


    public String updateByPatient(AppointmentUpdateRequest request) {
        log.info("11");
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_FOUND));
        log.info("22");
        appointment.setAppointmentStatus(AppointmentStatus.Cancelled.name());
        String note = "Bệnh nhân đã tự hủy lịch ";
        if (request.getAppointmentNote() != null) {
            note +="vì lí do: " + request.getAppointmentNote();
        }
        appointment.setAppointmentNote(note);
        // theem vao history và xóa record hiện tại
        appointmentHistoryService.addAppointment(appointment);
        appointmentRepository.delete(appointment);
        /// Thiếu nhá ^-^
        // goọi mail thông báo hủy cho doc và patien
        mailService.sendCancelEmail(appointment);

        DoctorTimework dt = appointment.getDoctortimework();
        dt.setStatus(DoctorTimeworkStatus.Available.name());
        doctorTimeworkRepository.save(dt);

        return "Thanh cong";
    }

    public List<AppointmentReponse> getAppointmentsByDepartment(String username) {
        Department department = employeeRepository.findByemployeeUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND))
                .getDepartment();
        return appointmentRepository.findByDepartment(department.getDepartmentId()).stream()
                .map(this::mapToAppointmentResponse)
                .collect(Collectors.toList());
    }

    public List<AppointmentReponse> getAppointmentsByDoctorId(String username) {
        List<Appointment> re = appointmentRepository.findByDoctor(username);
        return re.stream()
                .map(this::mapToAppointmentResponse)
                .collect(Collectors.toList());
    }

    public List<AppointmentReponse> getAppointmentsByPatientId(String username) {
        return appointmentRepository.findByPatient(username).stream()
                .map(this::mapToAppointmentResponse)
                .collect(Collectors.toList());

    }

    @Override
    public List<AppointmentReponse> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(this::mapToAppointmentResponse)
                .collect(Collectors.toList());
    }

    private LocalDate getDateFromWeekAndDayOfWeek(int week, int year, DayOfWeek dayOfWeek) {
        // Validate week number
        if (week < 1 || week > 53) {
            throw new AppException(ErrorCode.CAN_NOT_CONVERT);
        }
        LocalDate firstDayOfYear = LocalDate.of(year, 1, 1);
        WeekFields weekFields = WeekFields.of(vietnam);
        LocalDate date =  firstDayOfYear
                .with(weekFields.weekOfWeekBasedYear(), week)
                .with(weekFields.dayOfWeek(), dayOfWeek.getValue());
        return date;
    }

    private AppointmentReponse mapToAppointmentResponse(Appointment  appointments) {

        return new AppointmentReponse(
                appointments.getAppointmentId(),

                appointments.getPatient().getPatientName(),
                appointments.getDoctorservice().getDoctor().getDoctorName(),

                appointments.getDoctortimework().getYear(),
                appointments.getDoctortimework().getWeekOfYear(),
                appointments.getDoctortimework().getTimeWork().getDayOfWeek().toLowerCase(),
                appointments.getDoctortimework().getTimeWork().getStartTime().format(FORMATTIME),
                appointments.getDoctortimework().getTimeWork().getEndTime().format(FORMATTIME),

                appointments.getDoctorservice().getService().getServiceName(),

                appointments.getAppointmentStatus(),

                appointments.getEmployee() != null ? appointments.getEmployee().getEmployeeId().toString() : null,
                appointments.getEmployee() != null ? appointments.getEmployee().getEmployeeName() : null,
                appointments.getAppointmentNote()
        );
    }

}
