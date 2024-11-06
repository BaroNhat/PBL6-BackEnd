package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.EmployeeRequest;
import PBL6.example.UNIME.dto.response.EmployeeResponse;
import PBL6.example.UNIME.entity.Department;
import PBL6.example.UNIME.entity.Employee;
import PBL6.example.UNIME.entity.User;
import PBL6.example.UNIME.enums.Role;
import PBL6.example.UNIME.exception.AppException;
import PBL6.example.UNIME.exception.ErrorCode;
import PBL6.example.UNIME.repository.DepartmentRespository;
import PBL6.example.UNIME.repository.EmployeeRepository;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeService {
    EmployeeRepository employeeRepository;
    DepartmentRespository departmentRespository;
    UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    public EmployeeResponse createEmployee(@Valid EmployeeRequest request) {
        // Tìm department dựa trên departmentId từ request
        Department department = departmentRespository.findById(request.getDepartmentId())
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
        //1. kiểm tra, khởi tạo user
        User userTest = new User();
        userTest.setUsername(request.getEmployeeUsername());
        userTest.setPassword(request.getEmployeePassword());
        userTest.setEmail(request.getEmployeeEmail());
        userTest.setRole(Role.EMPLOYEE.name());
        User user = userService.createUser(userTest);
        // 2. Tạo Employee
        Employee employee = new Employee();
        employee.setEmployeeUserId(user);
        employee.setEmployeeName(request.getEmployeeName());
        employee.setEmployeePhonenumber(request.getEmployeePhoneNumber());
        employee.setEmployeeGender(request.getEmployeeGender());
        employee.setEmployeeStatus("ON");
        employee.setDepartment(department);

        return mapToResponse(employeeRepository.save(employee));

    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<EmployeeResponse> getAllEmployee(){
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @PostAuthorize("hasRole('ADMIN')")
    public EmployeeResponse getEmployeeById(Integer employeeId) {
        return mapToResponse(employeeRepository.findById(employeeId)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND)));
    }


    public EmployeeResponse getMyInfo(String Username) {

        User user = userService.getUserByUsername(Username);
        Employee employee = employeeRepository.findByemployeeUserId(user)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXITED));

        return mapToResponse(employee);
    }

//    @PostAuthorize("returnObject.patientUsername == authentication.name")
    public EmployeeResponse updateMyInfo(String employee_username, EmployeeRequest request){
        //1. kiểm tra tồn tại Employee theo Username
        Employee employee = employeeRepository.findByemployeeUserId(userService.getUserByUsername(employee_username))
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));

        //2. Kiểm tra  mail cập nhật có trùng với tài khoản khác
        if(userService.ExitByEmail(request.getEmployeeEmail())){
            User userByEmail = userService.getUserByEmail(request.getEmployeeEmail());
            if(!userByEmail.getUserId().equals(employee.getEmployeeUserId().getUserId())) {
                throw new AppException(ErrorCode.EMAIL_ALREADY_REGISTERED);
            }
        }
        // 3. cập nhật vào bảng User
        User user = new User();
        user.setPassword(request.getEmployeePassword());
        user.setEmail(request.getEmployeeEmail());
        userService.updateUser(employee.getEmployeeUserId().getUserId(), user);

        employee.setEmployeeName(request.getEmployeeName());
        employee.setEmployeePhonenumber(request.getEmployeePhoneNumber());
        employee.setEmployeeGender(request.getEmployeeGender());
        return mapToResponse(employeeRepository.save(employee));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public EmployeeResponse updateByAdmin(String employee_username, EmployeeRequest request){
        //1. kiểm tra tồn tại Employee theo Username
        Employee employee = employeeRepository.findByemployeeUserId(userService.getUserByUsername(employee_username))
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));

        // Kiểm tra sự tồn tại của departmentId trong bảng Department
        Department department = departmentRespository.findById(request.getDepartmentId())
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));

        employee.setEmployeeName(request.getEmployeeName());
        employee.setEmployeePhonenumber(request.getEmployeePhoneNumber());
        employee.setEmployeeGender(request.getEmployeeGender());
        return mapToResponse(employeeRepository.save(employee));
    }

    @PostAuthorize("hasRole('ADMIN')")
    public void deleteEmployee(Integer employee_id) {
        Employee employee = employeeRepository.findById(employee_id)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_FOUND));
        employeeRepository.deleteById(employee.getEmployeeId());
        userService.deleteUser(employee.getEmployeeUserId().getUserId());
    }

    private EmployeeResponse mapToResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getEmployeeId(),
                employee.getEmployeeUserId().getUserId(),
                employee.getEmployeeUserId().getUsername(),
                employee.getEmployeeUserId().getPassword(),
                employee.getEmployeeUserId().getEmail(),
                employee.getEmployeeName(),
                employee.getEmployeePhonenumber(),
                employee.isEmployeeGender(),
                employee.getEmployeeStatus(),
                employee.getDepartment().getDepartmentId()
        );
    }
}
