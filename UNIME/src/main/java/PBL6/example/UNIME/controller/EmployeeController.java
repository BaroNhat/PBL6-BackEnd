package PBL6.example.UNIME.controller;

import PBL6.example.UNIME.dto.request.EmployeeRequest;
import PBL6.example.UNIME.dto.response.ApiResponse;
import PBL6.example.UNIME.dto.response.EmployeeResponse;
import PBL6.example.UNIME.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    ApiResponse<String> createEmployee(@RequestBody @Valid EmployeeRequest request){
        employeeService.createEmployee(request);
        return ApiResponse.<String>builder()
                .result("SUCCESS")
                .build();
    }

    @PostAuthorize("hasRole('ADMIN')")
    @GetMapping
    ApiResponse<List<EmployeeResponse>> getAllEmployees(){
        return ApiResponse.<List<EmployeeResponse>>builder()
                .result(employeeService.getAllEmployee())
                .build();
    }

    @PostAuthorize("hasRole('ADMIN')")
    @GetMapping("/{employee_id}")
    ApiResponse<EmployeeResponse> getEmployeeById(@PathVariable("employee_id") Integer employeeId){
        return ApiResponse.<EmployeeResponse>builder()
                .result(employeeService.getEmployeeById(employeeId))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/updateDepartment/")
    ApiResponse<String> changeEmployeeDepartment(@RequestParam("employee_id") Integer employeeId, @RequestParam("department_id") Integer departmentId){
        return ApiResponse.<String>builder()
                .result("Employee has been change to " + employeeService.changeEmployeeDepartment(employeeId, departmentId).getDepartmentName())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/updateStatus/{employee_id}")
    ApiResponse<String> changeEmployeeStatus(@PathVariable("employee_id") Integer employeeId){
        return ApiResponse.<String>builder()
                .result("Employee has been change to " + employeeService.changeEmployeeStatus(employeeId).getStatus())
                .build();
    }

// ===
    @GetMapping("/myInfo")
    ApiResponse<EmployeeResponse> getEmployeeInfo(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        return ApiResponse.<EmployeeResponse>builder()
                .result(employeeService.getMyInfo(authentication.getName()))
                .build();
    }

    @PutMapping("/update/myInfo")
    ApiResponse<String> updateMyInfo(@RequestBody EmployeeRequest request){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        employeeService.updateMyInfo(authentication.getName(), request);
        return ApiResponse.<String>builder()
                .result("SUCCESS")
                .build();
    }

}
