package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.AppointmentRequest;
import PBL6.example.UNIME.entity.Appointment;
import PBL6.example.UNIME.entity.Patient;
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

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class AppointmentService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    PatientRepository patientRepository;
    DoctorTimeworkRepository doctorTimeworkRepository;
    EmployeeRepository employeeRepository;
    DoctorServiceRepository doctorServiceRepository;
    AppointmentRepository appointmentRepository;
    private final PatientService patientService;


    public String createAppointment(String username, AppointmentRequest request){
        Patient patient = patientService.getPatientByUsername(username);

        Appointment appointment = new Appointment();
        appointment.setPatient(patientRepository.findById( patient.getPatientId())
                .orElseThrow(()-> new AppException(ErrorCode.PATIENT_NOT_FOUND)));
        appointment.setDoctortimework(doctorTimeworkRepository.findById(request.getDoctortimeworkId())
                .orElseThrow(()-> new AppException(ErrorCode.DOCTORTIMEWORK_NOT_FOUND)));
        appointment.setDoctorservice(doctorServiceRepository.findById(request.getDoctorserviceId())
                .orElseThrow(()-> new AppException(ErrorCode.DOCTORSERVICE_NOT_FOUND)));
        appointmentRepository.save(appointment);

        return "Thanh cong";
    }


    private LocalDateTime validateAppointmentCreatedAt(String appointmentCreatedAt) {
        if (appointmentCreatedAt == null || appointmentCreatedAt.trim().isEmpty()) {
            throw new IllegalArgumentException("Appointment created time cannot be null or empty");
        }
        LocalDateTime parsedDateTime;
        try {
            parsedDateTime = LocalDateTime.parse(appointmentCreatedAt, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new AppException(ErrorCode.INVALID_DATETIME_FORMAT);
        }
        if (parsedDateTime.isAfter(LocalDateTime.now().plusWeeks(2))) {
            throw new AppException(ErrorCode.INVALID_DATETIME_FORMAT);
        }
        if (parsedDateTime.isBefore(LocalDateTime.now().plusDays(2))) {
            throw new AppException(ErrorCode.INVALID_DATETIME_FORMAT);
        }
        return parsedDateTime;
    }
}
