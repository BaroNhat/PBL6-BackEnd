package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.EmployeeRequest;
import PBL6.example.UNIME.dto.response.EmployeeDetailResponse;
import PBL6.example.UNIME.dto.response.EmployeeListResponse;
import PBL6.example.UNIME.entity.Department;
import PBL6.example.UNIME.entity.Employee;
import PBL6.example.UNIME.entity.User;
import PBL6.example.UNIME.enums.Role;
import PBL6.example.UNIME.exception.AppException;
import PBL6.example.UNIME.exception.ErrorCode;
import PBL6.example.UNIME.repository.EmployeeRepository;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeService {
    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);
    EmployeeRepository employeeRepository;
    UserService userService;
    DepartmentService departmentService;

    @PreAuthorize("hasRole('ADMIN')")
    public void createEmployee(@Valid EmployeeRequest request) {
        // Tìm department dựa trên departmentId từ request
        Department department = departmentService.getDepartmentByName(request.getDepartmentName());
        //1. kiểm tra, khởi tạo user
        User user = new User();
        user.setUsername(request.getEmployeeUsername());
        user.setPassword(request.getEmployeePassword());
        user.setEmail(request.getEmployeeEmail());
        user.setRole(Role.EMPLOYEE.name());

        // 2. Tạo Employee
        Employee employee = new Employee();
        employee.setEmployeeImage(request.getEmployeeImage());
        employee.setEmployeeUserId(userService.createUser(user));
        employee.setEmployeeName(request.getEmployeeName());
        employee.setEmployeePhonenumber(request.getEmployeePhoneNumber());
        employee.setEmployeeGender(request.getEmployeeGender());
        employee.setEmployeeStatus("ON");
        employee.setDepartment(department);
        employeeRepository.save(employee);
    }

    @PostAuthorize("hasRole('ADMIN')")
    public List<EmployeeListResponse> getAllEmployee(){
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(this::mapToEmployeeListResponse)
                .collect(Collectors.toList());
    }

    @PostAuthorize("hasRole('ADMIN')")
    public EmployeeDetailResponse getEmployeeById(Integer employeeId) {
        return mapToEmployeeDetailResponse(employeeRepository.findById(employeeId)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND)));
    }


    @PreAuthorize("hasRole('ADMIN')")
    public void updateByAdmin(Integer employee_id, EmployeeRequest request){

        //1. kiểm tra tồn tại Employee theo Username
        Employee employee = employeeRepository.findById(employee_id)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));

        //2. Kiểm tra sự tồn tại của departmentId trong bảng Department
        Department department = departmentService.getDepartmentByName(request.getDepartmentName());

        //3. Cập nhật Status, Department
        employee.setDepartment(department);
        employee.setEmployeeStatus(request.getEmployeeStatus());
        employeeRepository.save(employee);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteEmployee(Integer employee_id) {
        Employee employee = employeeRepository.findById(employee_id)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));
        employeeRepository.delete(employee);
        userService.deleteUser(employee.getEmployeeUserId().getUserId());
    }

//  ==== Token
    public EmployeeDetailResponse getMyInfo(String employee_username) {
        User user = userService.getUserByUsername(employee_username);
        Employee employee = employeeRepository.findByemployeeUserId(user)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXITED));

        return mapToEmployeeDetailResponse(employee);
    }

    public void updateMyInfo(String employee_username, EmployeeRequest request){
        //1. kiểm tra tồn tại Employee theo Username
        Employee employee = employeeRepository.findByemployeeUserId(userService.getUserByUsername(employee_username))
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));

        // 2. cập nhật vào bảng User
        User user = new User();
        user.setEmail(request.getEmployeeEmail());
        userService.updateUser(employee.getEmployeeUserId().getUserId(), user);

        employee.setEmployeeImage(employee.getEmployeeImage());
        employee.setEmployeeName(request.getEmployeeName());
        employee.setEmployeePhonenumber(request.getEmployeePhoneNumber());
        employee.setEmployeeGender(request.getEmployeeGender());
        employeeRepository.save(employee);
    }
// =========
    public Employee getEmployeeByUsername(String username) {
        User user = userService.getUserByUsername(username);
        Employee employee = employeeRepository.findByemployeeUserId(user)
                .orElseThrow( ()-> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));

        return employee;
    }
    public Employee findEmployeeById(Integer employeeId){
        return employeeRepository.findById(employeeId).orElseThrow(()-> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));
    }
    private EmployeeDetailResponse mapToEmployeeDetailResponse(Employee employee) {
        return new EmployeeDetailResponse(
                employee.getEmployeeId(),

                employee.getEmployeeUserId().getUsername(),
                employee.getEmployeeUserId().getEmail(),

                employee.getEmployeeImage(),
                employee.getEmployeeName(),
                employee.getEmployeePhonenumber(),
                employee.isEmployeeGender(),
                employee.getEmployeeStatus(),

                employee.getDepartment().getDepartmentName()
        );
    }
    private EmployeeListResponse mapToEmployeeListResponse(Employee employee) {
        return new EmployeeListResponse(

                employee.getEmployeeId(),
                employee.getEmployeeImage(),
                employee.getEmployeeName(),
                employee.getEmployeePhonenumber(),
                employee.isEmployeeGender(),
                employee.getEmployeeStatus(),

                employee.getDepartment().getDepartmentName()
        );
    }

}
