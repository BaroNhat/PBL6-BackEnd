package PBL6.example.UNIME.controller;

import PBL6.example.UNIME.dto.request.EmployeeRequest;
import PBL6.example.UNIME.dto.response.ApiResponse;
import PBL6.example.UNIME.dto.response.EmployeeDetailResponse;
import PBL6.example.UNIME.dto.response.EmployeeListResponse;
import PBL6.example.UNIME.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeController {
    EmployeeService employeeService;

    @PostMapping
    ApiResponse<String> createEmployee(@RequestBody @Valid EmployeeRequest request){
        employeeService.createEmployee(request);
        return ApiResponse.<String>builder()
                .result("SUCCESS")
                .build();
    }

    @GetMapping
    ApiResponse<List<EmployeeListResponse>> getAllEmployees(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<List<EmployeeListResponse>>builder()
                .result(employeeService.getAllEmployee())
                .build();
    }

    @GetMapping("/{employee_id}")
    ApiResponse<EmployeeDetailResponse> getEmployeeById(@PathVariable("employee_id") Integer employeeId){
        return ApiResponse.<EmployeeDetailResponse>builder()
                .result(employeeService.getEmployeeById(employeeId))
                .build();
    }

    @PutMapping("/update/{employee_id}")
    ApiResponse<String> updateByAdmin(@PathVariable("employee_id") Integer employeeId, @RequestBody EmployeeRequest request){
        employeeService.updateByAdmin(employeeId, request);
        return ApiResponse.<String>builder()
                .result("SUCCESS")
                .build();
    }

    @DeleteMapping("/{employee_id}")
    ApiResponse<String> deleteEmployee(@PathVariable("employee_id") Integer employeeId){

        employeeService.deleteEmployee(employeeId);
        return ApiResponse.<String>builder()
                .result("SUCCESS")
                .build();
    }

// ===
    @GetMapping("/myInfo")
    ApiResponse<EmployeeDetailResponse> getEmployeeInfo(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        return ApiResponse.<EmployeeDetailResponse>builder()
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
