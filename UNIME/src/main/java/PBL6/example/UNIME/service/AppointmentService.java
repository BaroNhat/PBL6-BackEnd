package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.AppointmentCreateRequest;
import PBL6.example.UNIME.dto.request.AppointmentUpdateRequest;
import PBL6.example.UNIME.dto.response.AppointmentReponse;

import java.util.List;

public interface AppointmentService {

     AppointmentReponse createAppointment(String username, AppointmentCreateRequest request);
     String updateByDoctor( AppointmentUpdateRequest request);
     String updateByEmployee(String username, AppointmentUpdateRequest request);
     String updateByPatient(AppointmentUpdateRequest request);
     List<AppointmentReponse> getAppointmentsByDepartment(String username);
     List<AppointmentReponse> getAppointmentsByDoctorId(String username);
     List<AppointmentReponse> getAppointmentsByPatientId(String username);
     List<AppointmentReponse> getAllAppointments();

}
