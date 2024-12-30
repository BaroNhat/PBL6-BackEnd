package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.response.AppointmentReponse;
import PBL6.example.UNIME.entity.Appointment;
import PBL6.example.UNIME.entity.AppointmentHistory;
import PBL6.example.UNIME.entity.Department;
import PBL6.example.UNIME.exception.AppException;
import PBL6.example.UNIME.exception.ErrorCode;
import PBL6.example.UNIME.repository.AppointmentHistoryRepository;
import PBL6.example.UNIME.repository.EmployeeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class AppointmentHistoryServiceImpl implements   AppointmentHistoryService {

    AppointmentHistoryRepository appointmentHistoryRepository;
    EmployeeRepository employeeRepository;


    @Override
    public void addAppointment(Appointment appointment) {
        AppointmentHistory ah = mapToAppointmentHistory(appointment);
        appointmentHistoryRepository.save(ah);
    }

    @Override
    public List<AppointmentReponse> getByDoctor(String username) {
        List<AppointmentHistory> appointmentHistoryList = appointmentHistoryRepository.findBydoctorUsername(username);
        return appointmentHistoryList.stream()
                .map(this::mapToAppointmentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentReponse> getByPatient(String username) {
        List<AppointmentHistory> appointmentHistoryList = appointmentHistoryRepository.findBypatientUsername(username);
        return appointmentHistoryList.stream()
                .map(this::mapToAppointmentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentReponse> getByDepartment(String username) {
        Department department = employeeRepository.findByemployeeUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND))
                .getDepartment();
        List<AppointmentHistory> appointmentHistoryList = appointmentHistoryRepository.findBydepartmentId(department.getDepartmentId());
        return appointmentHistoryList.stream()
                .map(this::mapToAppointmentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentReponse> getAllAppointments() {
        return appointmentHistoryRepository.findAll().stream()
                .map(this::mapToAppointmentResponse)
                .collect(Collectors.toList());
    }


    public AppointmentHistory mapToAppointmentHistory(Appointment appointment) {
        if (appointment == null) {
            return null;
        }

        AppointmentHistory history = new AppointmentHistory();
        history.setAppointmentId(appointment.getAppointmentId());
        history.setPatientUsername(appointment.getPatient().getPatientUser().getUsername());
        history.setDoctorUsername(appointment.getDoctorservice().getDoctor().getDoctorUserId().getUsername());
        history.setDepartmentId(appointment.getDoctorservice().getDoctor().getDepartment().getDepartmentId());
        history.setPatientName(appointment.getPatient().getPatientName());
        history.setDoctorName(appointment.getDoctorservice().getDoctor().getDoctorName());
        history.setDoctorTimeWorkYear(appointment.getDoctortimework().getYear());
        history.setWeekOfYear(appointment.getDoctortimework().getWeekOfYear());
        history.setDayOfWeek(appointment.getDoctortimework().getTimeWork().getDayOfWeek().toLowerCase());
        history.setStartTime(appointment.getDoctortimework().getTimeWork().getStartTime());
        history.setEndTime(appointment.getDoctortimework().getTimeWork().getEndTime());
        history.setServiceName(appointment.getDoctorservice().getService().getServiceName());
        history.setAppointmentStatus(appointment.getAppointmentStatus());
        history.setAppointmentNote(appointment.getAppointmentNote());

        history.setEmployeeId(
                appointment.getEmployee() != null ? appointment.getEmployee().getEmployeeId() : null
        );
        history.setEmployeeName(
                appointment.getEmployee() != null ? appointment.getEmployee().getEmployeeName() : null
        );

        return history;
    }
    public AppointmentReponse mapToAppointmentResponse(AppointmentHistory history) {
        if (history == null) {
            return null; // Handle null input
        }

        // Mapping fields from AppointmentHistory to AppointmentResponse
        AppointmentReponse response = new AppointmentReponse();

        response.setAppointmentId(history.getAppointmentId());
        response.setPatientName(history.getPatientName());
        response.setDoctorName(history.getDoctorName());
        response.setYear(history.getDoctorTimeWorkYear());
        response.setWeekOfYear(history.getWeekOfYear());
        response.setDayOfWeek(history.getDayOfWeek());
        response.setStartTime(history.getStartTime().toString()); // Converting LocalTime to String
        response.setEndTime(history.getEndTime().toString());     // Converting LocalTime to String
        response.setServiceName(history.getServiceName());
        response.setAppointmentStatus(history.getAppointmentStatus());
        response.setAppointmentNote(history.getAppointmentNote());

        response.setEmployeeId(
                history.getEmployeeId() != null ? history.getEmployeeId().toString() : null
        );
        response.setEmployeeName(history.getEmployeeName());

        return response;
    }
}
