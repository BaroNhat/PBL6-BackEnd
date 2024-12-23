package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.AppointmentCreateRequest;
import PBL6.example.UNIME.dto.request.AppointmentUpdateRequest;
import PBL6.example.UNIME.dto.response.AppointmentReponse;
import PBL6.example.UNIME.dto.response.PatientResponse;
import PBL6.example.UNIME.entity.*;
import PBL6.example.UNIME.enums.AppointmentStatus;
import PBL6.example.UNIME.enums.DayOfWeek;
import PBL6.example.UNIME.enums.DoctorTimeworkStatus;
import PBL6.example.UNIME.enums.Role;
import PBL6.example.UNIME.exception.AppException;
import PBL6.example.UNIME.exception.ErrorCode;
import PBL6.example.UNIME.repository.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class AppointmentService {

    DateTimeFormatter FORMATTIME = DateTimeFormatter.ofPattern("HH:mm");
    DateTimeFormatter FORMATDATETIME = DateTimeFormatter.ofPattern("HH:mm");
    PatientRepository patientRepository;
    DoctorTimeworkRepository doctorTimeworkRepository;
    DoctorServiceRepository doctorServiceRepository;
    AppointmentRepository appointmentRepository;
    PatientService patientService;
    EmployeeService employeeService;
    DoctorService doctorService;
    DoctorTimeworkService doctorTimeworkService;


    public String createAppointment(String username, AppointmentCreateRequest request) {
        Patient patient = patientService.getPatientByUsername(username);
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

        if(!AppointmentStatus.contains(request.getAppointmentStatus())) throw new AppException(ErrorCode.INVALID_KEY);
        appointment.setAppointmentStatus(request.getAppointmentStatus());

        appointmentRepository.save(appointment);
        return "Thanh cong";
    }


    public String updateByEmployee(String username, AppointmentUpdateRequest request) {
        Employee employee = employeeService.getEmployeeByUsername(username);
        log.info("11");
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_FOUND));
        log.info("22");
        if(!AppointmentStatus.contains(request.getAppointmentStatus())) throw new AppException(ErrorCode.INVALID_KEY);
        appointment.setAppointmentStatus(request.getAppointmentStatus());

        if (request.getAppointmentNote() != null) {
            appointment.setAppointmentNote(request.getAppointmentNote());
        }
        appointment.setEmployee(employee);


        appointmentRepository.save(appointment);

        return "Thanh cong";
    }

    // GetList for Employee
    public List<AppointmentReponse> getAppointmentsByDepartment(String username) {
        Department department = employeeService.getEmployeeByUsername(username).getDepartment();
        return appointmentRepository.findByDepartment(department.getDepartmentId()).stream()
                .map(this::mapToAppointmentResponse)
                .collect(Collectors.toList());
    }

    // GetList for Doctor
    public List<AppointmentReponse> getAppointmentsByDoctorId(String username) {
        Doctor doctor = doctorService.getDoctorByUsername(username);
        return appointmentRepository.findByDoctor(doctor.getDoctorId()).stream()
                .map(this::mapToAppointmentResponse)
                .collect(Collectors.toList());
    }

    public List<AppointmentReponse> getAppointmentsByPatientId(String username) {
        List<Appointment> list = patientService.getAppointments(username);
        return list.stream()
                .map(this::mapToAppointmentResponse)
                .collect(Collectors.toList());

    }

    public AppointmentReponse mapToAppointmentResponse(Appointment  appointments) {

        return new AppointmentReponse(
                appointments.getAppointmentId(),

                appointments.getPatient().getPatientName(),
                appointments.getDoctorservice().getDoctor().getDoctorName(),

                appointments.getDoctortimework().getTimeWork().getDayOfWeek().toLowerCase(),
                appointments.getDoctortimework().getTimeWork().getStartTime().format(FORMATTIME),
                appointments.getDoctortimework().getTimeWork().getEndTime().format(FORMATTIME),

                appointments.getDoctorservice().getService().getServiceName(),

                appointments.getAppointmentCreatedAt().format(FORMATDATETIME),
                appointments.getAppointmentStatus(),

                appointments.getEmployee() != null ? appointments.getEmployee().getEmployeeId().toString() : null,
                appointments.getEmployee() != null ? appointments.getEmployee().getEmployeeName() : null,
                appointments.getAppointmentNote()
        );
    }

    public AppointmentReponse mapToAppointmentResponse(Map<String, Object> map) {
        return AppointmentReponse.builder()
                .appointmentId((Integer) map.get("appointmentId"))
                .patientName((String) map.get("patientName"))
                .doctorName((String) map.get("doctorName"))
                .dayOfWeek((String) map.get("dayOfWeek"))
                .startTime(((LocalTime) map.get("startTime")).format(FORMATTIME))
                .endTime(((LocalTime) map.get("endTime")).format(FORMATTIME))
                .serviceName((String) map.get("serviceName"))
                .appointmentCreatedAt(((LocalDateTime) map.get("appointmentCreatedAt")).format(FORMATDATETIME)) // Định dạng ngày giờ
                .appointmentStatus((String) map.get("appointmentStatus"))
                .employeeId((String) map.get("employeeId"))
                .employeeName((String) map.get("employeeName"))
                .appointmentNote((String) map.get("appointmentNote"))
                .build();
    }
}
