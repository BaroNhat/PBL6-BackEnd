package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.EmployeeRequest;
import PBL6.example.UNIME.dto.response.EmployeeResponse;
import PBL6.example.UNIME.entity.Department;
import PBL6.example.UNIME.entity.Employee;
import PBL6.example.UNIME.entity.User;
import jakarta.validation.Valid;

import java.util.List;

public interface EmployeeService {

    public void createEmployee(@Valid EmployeeRequest request);
    public List<EmployeeResponse> getAllEmployee();
    public EmployeeResponse getEmployeeById(Integer employeeId);
    public Department changeEmployeeDepartment(Integer employee_id, Integer departmentId);
    public User changeEmployeeStatus(Integer employee_id);
    public EmployeeResponse getMyInfo(String employee_username);
    public void updateMyInfo(String employee_username, EmployeeRequest request);
    public Employee getEmployeeByUsername(String username);


}
