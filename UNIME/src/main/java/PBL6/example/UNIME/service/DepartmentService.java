package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.DepartmentRequest;
import PBL6.example.UNIME.dto.response.DepartmentResponse;
import PBL6.example.UNIME.entity.Department;

import java.util.List;

public interface DepartmentService {

    public DepartmentResponse createDepartment(DepartmentRequest request);
    public DepartmentResponse updateDepartment(Integer departmentId, DepartmentRequest request);
    public void deleteDeparment(Integer departmentId);
    public Department getDepartmentById(Integer departmentId);
    public List<DepartmentResponse> getAllDepartments();
    public List<DepartmentResponse> findDepartmentByName(String departmentName);

}
