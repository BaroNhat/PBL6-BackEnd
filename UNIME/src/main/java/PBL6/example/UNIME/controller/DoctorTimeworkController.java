package PBL6.example.UNIME.controller;


import PBL6.example.UNIME.dto.request.DoctorTimeworkCreateRequest;
import PBL6.example.UNIME.dto.request.DoctorTimeworkUpdateRequest;
import PBL6.example.UNIME.dto.response.ApiResponse;
import PBL6.example.UNIME.dto.response.DoctorTimeworkResponse;
import PBL6.example.UNIME.entity.Doctor;
import PBL6.example.UNIME.entity.Employee;
import PBL6.example.UNIME.exception.AppException;
import PBL6.example.UNIME.exception.ErrorCode;
import PBL6.example.UNIME.repository.DoctorRepository;
import PBL6.example.UNIME.service.DoctorService;
import PBL6.example.UNIME.service.DoctorTimeworkService;
import PBL6.example.UNIME.service.EmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final EmployeeService employeeService;
    private final DoctorRepository doctorRepository;

    @PostMapping()
    public ApiResponse<String> addDoctorTimework(@RequestBody List<DoctorTimeworkCreateRequest> requests) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        doctorTimeworkService.createDoctorTimework(authentication.getName(), requests);
        return ApiResponse.<String>builder()
                .result("All doctor timeworks have been created successfully.")
                .build();
    }

    @GetMapping("/get/listByWeek/{week_year}") // 3s
    public ApiResponse<List<DoctorTimeworkResponse>> getAllDoctorTimeworkByWeek(@PathVariable("week_year") String week_year) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = employeeService.getEmployeeByUsername(authentication.getName());
        List<DoctorTimeworkResponse> list = doctorTimeworkService.getAllDoctorTimeworkByWeek(employee, week_year);
        return ApiResponse.<List<DoctorTimeworkResponse>>builder()
                .result(list)
                .build();
    }

    @GetMapping("/get/listByDoctor/{doctor_id}")  // 1s
    public ApiResponse<List<DoctorTimeworkResponse>> getListTimeworkOfDoctor(@PathVariable("doctor_id") Integer doctorId) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(()-> new AppException(ErrorCode.DOCTORS_NOT_FOUND));

        List<DoctorTimeworkResponse> list = doctorTimeworkService.getListTimeworkOfDoctor(doctor);
        return ApiResponse.<List<DoctorTimeworkResponse>>builder()
                .result(list)
                .build();
    }

    @PutMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ApiResponse<String> UpdateDoctorTimeworkById(@RequestBody DoctorTimeworkUpdateRequest requests) {
        return ApiResponse.<String>builder()
                .result(doctorTimeworkService.updateDoctorTimework(requests.getDoctorTimeworkId(), requests.getDoctorTimeworkStatus()))
                .build();
    }
}