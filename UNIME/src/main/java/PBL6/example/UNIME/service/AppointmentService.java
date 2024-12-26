package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.AppointmentCreateRequest;
import PBL6.example.UNIME.dto.request.AppointmentUpdateRequest;
import PBL6.example.UNIME.dto.response.AppointmentReponse;

import java.util.List;

public interface AppointmentService {

    public String createAppointment(String username, AppointmentCreateRequest request);
    public String updateByDoctor( AppointmentUpdateRequest request);
    public String updateByEmployee(String username, AppointmentUpdateRequest request);
    public List<AppointmentReponse> getAppointmentsByDepartment(String username);
    public List<AppointmentReponse> getAppointmentsByDoctorId(String username);
    public List<AppointmentReponse> getAppointmentsByPatientId(String username);

    }
