package PBL6.example.UNIME.controller;

import PBL6.example.UNIME.dto.request.DoctorRequest;
import PBL6.example.UNIME.dto.response.ApiResponse;
import PBL6.example.UNIME.dto.response.DoctorResponse;
import PBL6.example.UNIME.service.DoctorService;
import PBL6.example.UNIME.service.DoctorServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponse<String> createDoctor(@RequestBody @Valid DoctorRequest request){
        doctorService.createDoctor(request);
        return ApiResponse.<String>builder()
                .result("SUCCESS").build();
    }

    @PatchMapping("/updateDepartment/")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponse<String> changeDoctorDepartment(@RequestParam("doctor_id") Integer doctorId,@RequestParam("department_id") Integer departmentId){
        return ApiResponse.<String>builder()
                .result("Doctor has been change to "+ doctorService.changeDoctorDepartment(doctorId, departmentId).getDepartmentName())
                .build();
    }

    @PatchMapping("/updateStatus/{doctor_id}")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponse<String> deleteDoctor(@PathVariable("doctor_id") Integer doctorId){
        return ApiResponse.<String>builder()
                .result("Doctor has been change to "+ doctorService.changeDoctorStatus(doctorId).getStatus())
                .build();
    }

    @GetMapping("/get/getDetail/{doctor_id}")//1s
    ApiResponse<DoctorResponse> getDoctorById(@PathVariable("doctor_id") Integer doctorId){
        return ApiResponse.<DoctorResponse>builder()
                .result(doctorService.getDoctorById(doctorId))
                .build();
    }

    //====

    @GetMapping("/myInfo")
    ApiResponse<DoctorResponse> getDoctorInfo(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return ApiResponse.<DoctorResponse>builder()
                .result(doctorService.getMyInfo(authentication.getName()))
                .build();
    }
    @PutMapping("/update/myInfo")
    ApiResponse<String> updateMyInfo(@RequestBody DoctorRequest request){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        doctorService.updateMyInfo(authentication.getName(), request);
        return ApiResponse.<String>builder()
                .result("SUCCESS")
                .build();
    }

    //====

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/doctorList")// 2s
    ApiResponse<List<DoctorResponse>> getAllDoctors(){
        return ApiResponse.<List<DoctorResponse>>builder()
                .result(doctorService.getAllDoctors())
                .build();
    }

    @GetMapping("/get/doctorList")// 2s
    ApiResponse<List<DoctorResponse>> getAllDoctorsActive(){
        return ApiResponse.<List<DoctorResponse>>builder()
                .result(doctorService.getAllDoctorActive())
                .build();
    }

    @GetMapping("/get/byName") //1s
    ApiResponse<List<DoctorResponse>> getDoctorByName(@RequestParam("doctor_name") String doctorName){
        return ApiResponse.<List<DoctorResponse>>builder()
                .result(doctorService.getDoctorByName(doctorName))
                .build();
    }

    @GetMapping("/get/byDepartment")// 2s
    ApiResponse<List<DoctorResponse>> getDoctorByDepartment(@RequestParam("doctor_departmentId") Integer departmentId){
        return ApiResponse.<List<DoctorResponse>>builder()
                .result(doctorService.getDoctorByDepartment(departmentId))
                .build();
    }
}
