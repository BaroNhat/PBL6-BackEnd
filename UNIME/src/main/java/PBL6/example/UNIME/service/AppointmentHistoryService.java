package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.response.AppointmentReponse;
import PBL6.example.UNIME.entity.Appointment;

import java.util.List;

public interface AppointmentHistoryService {
    void addAppointment(Appointment appointment);
    List<AppointmentReponse> getByDoctor(String username);
    List<AppointmentReponse> getByPatient(String username);
    List<AppointmentReponse> getByDepartment(String username);
    List<AppointmentReponse> getAllAppointments();

}
