package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.DoctorRequest;
import PBL6.example.UNIME.dto.response.DoctorResponse;
import PBL6.example.UNIME.entity.Department;
import PBL6.example.UNIME.entity.Doctor;
import PBL6.example.UNIME.entity.User;
import jakarta.validation.Valid;

import java.util.List;

public interface DoctorService {

    public void createDoctor(@Valid DoctorRequest request);
    public Department changeDoctorDepartment(Integer doctor_id, Integer departmentId);
    public User changeDoctorStatus(Integer doctor_id);
    public DoctorResponse getDoctorById(Integer doctorId) ;
    public DoctorResponse getMyInfo(String Username);
    public void updateMyInfo(String doctor_username, DoctorRequest request);
    public List<DoctorResponse> getAllDoctors();
    public List<DoctorResponse> getAllDoctorActive();
    public List<DoctorResponse> getDoctorByName(String doctorName);
    public List<DoctorResponse> getDoctorByDepartment(Integer departmentId);
    public Doctor getDoctorByUsername(String Username);


}
