package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.AppointmentCreateRequest;
import PBL6.example.UNIME.dto.request.AppointmentUpdateRequest;
import PBL6.example.UNIME.dto.response.AppointmentReponse;
import PBL6.example.UNIME.entity.*;
import PBL6.example.UNIME.enums.AppointmentStatus;
import PBL6.example.UNIME.enums.DoctorTimeworkStatus;
import PBL6.example.UNIME.exception.AppException;
import PBL6.example.UNIME.exception.ErrorCode;
import PBL6.example.UNIME.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class AppointmentServiceImpl implements AppointmentService{

    DateTimeFormatter FORMATTIME = DateTimeFormatter.ofPattern("HH:mm");
    PatientRepository patientRepository;
    DoctorTimeworkRepository doctorTimeworkRepository;
    DoctorServiceRepository doctorServiceRepository;
    AppointmentRepository appointmentRepository;
    DoctorTimeworkService doctorTimeworkService;
    EmployeeRepository employeeRepository;
    AppointmentHistoryService appointmentHistoryService;


    public String createAppointment(String username, AppointmentCreateRequest request) {
        Patient patient = patientRepository.findBypatientUserUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.PATIENT_NOT_FOUND));
        DoctorTimework dtimework = doctorTimeworkRepository.findById(request.getDoctortimeworkId())
                .orElseThrow(() -> new AppException(ErrorCode.DOCTORTIMEWORK_NOT_FOUND));

        Appointment appointment = new Appointment();
        appointment.setPatient(patientRepository.findById(patient.getPatientId())
                .orElseThrow(() -> new AppException(ErrorCode.PATIENT_NOT_FOUND)));
        appointment.setDoctortimework(dtimework);
        appointment.setDoctorservice(doctorServiceRepository.findById(request.getDoctorserviceId())
                .orElseThrow(() -> new AppException(ErrorCode.DOCTORSERVICE_NOT_FOUND)));
        appointmentRepository.save(appointment);
        dtimework.setStatus(DoctorTimeworkStatus.Busy.name());
        log.info("3: {}", dtimework.getStatus());
        doctorTimeworkService.updateDoctorTimework(dtimework.getId(), dtimework.getStatus());

        return "Thanh cong";
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

        // theem vao history và xóa record hiện tại
        appointmentHistoryService.addAppointment(appointment);
        appointmentRepository.delete(appointment);
        /// Thiếu nhá ^-^
        // goọi mail thông báo hủy cho doc và patien
        DoctorTimework dt = appointment.getDoctortimework();
        dt.setStatus(DoctorTimeworkStatus.Available.name());
        doctorTimeworkRepository.save(dt);

        return "Thanh cong";
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
        appointmentRepository.save(appointment);
        // theem vao history và xóa record hiện tại
        appointmentHistoryService.addAppointment(appointment);
        appointmentRepository.delete(appointment);
        /// Thiếu nhá ^-^
        // goọi mail thông báo hủy cho doc và patien
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

    public AppointmentReponse mapToAppointmentResponse(Appointment  appointments) {

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
