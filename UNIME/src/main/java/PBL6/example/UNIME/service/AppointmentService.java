package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.AppointmentCreateRequest;
import PBL6.example.UNIME.dto.request.AppointmentUpdateRequest;
import PBL6.example.UNIME.dto.response.AppointmentReponse;
import PBL6.example.UNIME.dto.response.PatientResponse;
import PBL6.example.UNIME.entity.*;
import PBL6.example.UNIME.enums.AppointmentStatus;
import PBL6.example.UNIME.enums.DoctorTimeworkStatus;
import PBL6.example.UNIME.enums.Role;
import PBL6.example.UNIME.exception.AppException;
import PBL6.example.UNIME.exception.ErrorCode;
import PBL6.example.UNIME.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
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
    private final UserService userService;
    private final DoctorService doctorService;
    private final TimeworkRespository timeworkRespository;
    private final DoctorTimeworkService doctorTimeworkService;


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
        log.info("11");
        List<Appointment> appointments = appointmentRepository.findByDepartment(department.getDepartmentId());
        log.info("12");
        return appointments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // GetList for Doctor
    public List<AppointmentReponse> getAppointmentsByDoctorId(String username) {
        Doctor doctor = doctorService.getDoctorByUsername(username);
        List<Appointment> appointments = appointmentRepository.findByDoctor(doctor.getDoctorId());
        return appointments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

//    private LocalDateTime validateAppointmentCreatedAt(String appointmentCreatedAt) {
//        if (appointmentCreatedAt == null || appointmentCreatedAt.trim().isEmpty()) {
//            throw new IllegalArgumentException("Appointment created time cannot be null or empty");
//        }
//        LocalDateTime parsedDateTime;
//        try {
//            parsedDateTime = LocalDateTime.parse(appointmentCreatedAt, FORMATTER);
//        } catch (DateTimeParseException e) {
//            throw new AppException(ErrorCode.INVALID_DATETIME_FORMAT);
//        }
//        if (parsedDateTime.isAfter(LocalDateTime.now().plusWeeks(2))) {
//            throw new AppException(ErrorCode.INVALID_DATETIME_FORMAT);
//        }
//        if (parsedDateTime.isBefore(LocalDateTime.now().plusDays(2))) {
//            throw new AppException(ErrorCode.INVALID_DATETIME_FORMAT);
//        }
//        return parsedDateTime;
//    }

    private AppointmentReponse mapToResponse(Appointment appointment) {
        return new AppointmentReponse(
                appointment.getAppointmentId(),

                appointment.getPatient().getPatientName(),
                appointment.getDoctorservice().getDoctor().getDoctorName(),

                appointment.getDoctortimework().getTimeWork().getDayOfWeek().name(),
                appointment.getDoctortimework().getTimeWork().getStartTime().format(FORMATTIME),
                appointment.getDoctortimework().getTimeWork().getEndTime().format(FORMATTIME),

                appointment.getDoctorservice().getService().getServiceName(),
                appointment.getAppointmentCreatedAt().format(FORMATDATETIME),
                appointment.getAppointmentStatus(),

                appointment.getEmployee() != null ? appointment.getEmployee().getEmployeeName() : null,
                appointment.getAppointmentNote()
        );
    }
}
