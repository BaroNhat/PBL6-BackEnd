package PBL6.example.UNIME.controller;

import PBL6.example.UNIME.dto.request.DepartmentRequest;
import PBL6.example.UNIME.dto.response.ApiResponse;
import PBL6.example.UNIME.dto.response.DepartmentResponse;
import PBL6.example.UNIME.entity.Department;
import PBL6.example.UNIME.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    //==== Admin
    @PostMapping
    ApiResponse<DepartmentResponse> createDepartment(@RequestBody @Valid DepartmentRequest request) {
        ApiResponse<DepartmentResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(departmentService.createDepartment(request));
        return apiResponse;
    }

    @PutMapping("/{department_id}")
    ApiResponse<DepartmentResponse> updateDepartment(@PathVariable Integer department_id, @RequestBody DepartmentRequest request){
        return ApiResponse.<DepartmentResponse>builder()
                .result(departmentService.updateDepartment(department_id, request))
                .build();
    }

    @DeleteMapping("/{department_id}")
    ApiResponse<String> deleteDeparment(@PathVariable Integer department_id){
        departmentService.deleteDeparment(department_id);
        return ApiResponse.<String>builder()
                .result( "SUCCESS" )
                .build();
    }


    @GetMapping("/get/departmentList")
    ApiResponse<List<DepartmentResponse>> getAllDepartments() {
        return ApiResponse.<List<DepartmentResponse>>builder()
                .result(departmentService.getAllDepartments())
                .build();
    }

    @GetMapping("/get/")
    ApiResponse<List<DepartmentResponse>> findByDepartmentName (@RequestParam("department_name") String departmentName) {
        return ApiResponse.<List<DepartmentResponse>>builder()
                .result(departmentService.findDepartmentByName(departmentName))
                .build();
    }
}
