package PBL6.example.UNIME.controller;


import PBL6.example.UNIME.dto.request.DoctorTimeworkRequest;
import PBL6.example.UNIME.dto.response.ApiResponse;
import PBL6.example.UNIME.dto.response.DoctorTimeworkResponse;
import PBL6.example.UNIME.entity.Doctor;
import PBL6.example.UNIME.entity.DoctorTimework;
import PBL6.example.UNIME.entity.Employee;
import PBL6.example.UNIME.exception.AppException;
import PBL6.example.UNIME.exception.ErrorCode;
import PBL6.example.UNIME.service.DoctorService;
import PBL6.example.UNIME.service.DoctorTimeworkService;
import PBL6.example.UNIME.service.EmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/doctortimework")
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class DoctorTimeworkController {
    DoctorTimeworkService doctorTimeworkService;
    private final DoctorService doctorService;
    private final EmployeeService employeeService;

    @PostMapping()
    public ApiResponse<String> addDoctorTimework(@RequestBody List<DoctorTimeworkRequest> requests) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Doctor doctor = doctorService.getDoctorByUsername(authentication.getName());
        for (DoctorTimeworkRequest request : requests) {
            doctorTimeworkService.createDoctorTimework(doctor, request);
        }

        return ApiResponse.<String>builder()
                .result("All doctor timeworks have been created successfully.")
                .build();
    }

    @GetMapping("/get/listByWeek/{week_year}")
    public ApiResponse<List<DoctorTimeworkResponse>> getAllDoctorTimeworkByWeek(@PathVariable("week_year") String week_year) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = employeeService.getEmployeeByUsername(authentication.getName());
        List<DoctorTimeworkResponse> list = doctorTimeworkService.getAllDoctorTimeworkByWeek(employee, week_year);
        return ApiResponse.<List<DoctorTimeworkResponse>>builder()
                .result(list)
                .build();
    }
}