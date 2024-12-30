package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.EmployeeRequest;
import PBL6.example.UNIME.dto.response.EmployeeResponse;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class  EmployeeServiceImpl implements  EmployeeService {
    EmployeeRepository employeeRepository;

    DepartmentService departmentService;

    UserService userService;

    public void createEmployee(@Valid EmployeeRequest request) {
        Department department = departmentService.getDepartmentById(request.getDepartmentId());

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
        employee.setDepartment(department);
        employeeRepository.save(employee);
    }

    public List<EmployeeResponse> getAllEmployee(){
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(this::mapToEmployeeResponse)
                .collect(Collectors.toList());
    }

    public EmployeeResponse getEmployeeById(Integer employeeId) {
        return mapToEmployeeResponse(employeeRepository.findById(employeeId)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND)));
    }


    public Department changeEmployeeDepartment(Integer employee_id, Integer departmentId){
        Employee employee = employeeRepository.findById(employee_id)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));

        Department department = departmentService.getDepartmentById(departmentId);
        employee.setDepartment(department);
        employeeRepository.save(employee);
        return department;
    }

    public User changeEmployeeStatus(Integer employee_id) {
        Employee employee = employeeRepository.findById(employee_id)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));
        return userService.changeUserStatus(employee.getEmployeeUserId());
    }

//  ==== Token
    public EmployeeResponse getMyInfo(String employee_username) {
        Employee employee = employeeRepository.findByemployeeUsername(employee_username)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXITED));

        return mapToEmployeeResponse(employee);
    }

    public void updateMyInfo(String employee_username, EmployeeRequest request){
        //1. kiểm tra tồn tại Employee theo Username
        Employee employee = employeeRepository.findByemployeeUsername(employee_username)
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
        return employeeRepository.findByemployeeUsername(username)
                .orElseThrow( ()-> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));
    }
    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getEmployeeId(),

                employee.getEmployeeUserId().getUsername(),
                employee.getEmployeeUserId().getEmail(),
                employee.getEmployeeUserId().getStatus(),

                employee.getEmployeeImage(),
                employee.getEmployeeName(),
                employee.getEmployeePhonenumber(),
                employee.isEmployeeGender(),

                employee.getDepartment().getDepartmentName()
        );
    }

}
