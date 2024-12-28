package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.DoctorTimeworkCreateRequest;
import PBL6.example.UNIME.dto.response.DoctorTimeworkResponse;
import PBL6.example.UNIME.entity.Doctor;
import PBL6.example.UNIME.entity.Employee;

import java.util.List;

public interface DoctorTimeworkService {

    void createDoctorTimework(String  username, List<DoctorTimeworkCreateRequest> requests);
    String updateDoctorTimework(Integer doctorTimeworkId , String doctorTimeworkStatus);
    List<DoctorTimeworkResponse> getAllDoctorTimeworkByWeek(String username, String week_year);
    List<DoctorTimeworkResponse> getListAvailableTimeworkOfDoctor(Integer doctorId);
    List<DoctorTimeworkResponse> getListTimeworkOfDoctor(String username);
    void deleteDoctorTimework();

    }
