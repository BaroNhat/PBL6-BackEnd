package PBL6.example.UNIME.controller;

import PBL6.example.UNIME.dto.request.DoctorRequest;
import PBL6.example.UNIME.dto.response.ApiResponse;
import PBL6.example.UNIME.dto.response.DoctorDetailResponse;
import PBL6.example.UNIME.dto.response.DoctorListResponse;
import PBL6.example.UNIME.service.DoctorService;
import jakarta.validation.Valid;
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
@RequestMapping("/doctors")
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class DoctorController {

    DoctorService doctorService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponse<String> createDoctor(@RequestBody @Valid DoctorRequest request){
        doctorService.createDoctor(request);
        return ApiResponse.<String>builder()
                .result("SUCCESS").build();
    }

    @PutMapping("/update/{doctor_id}")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponse<String> updateByAdmin(@PathVariable("doctor_id") Integer doctorId,@RequestBody DoctorRequest request){
        doctorService.updateByAdmin(doctorId, request);
        return ApiResponse.<String>builder()
                .result("SUCCESS").build();
    }

    @DeleteMapping("/{doctor_id}")
    @PreAuthorize("hasRole('ADMIN')")
    ApiResponse<String> deleteDoctor(@PathVariable("doctor_id") Integer doctorId){

        doctorService.deleteDoctor(doctorId);
        return ApiResponse.<String>builder()
                .result("Doctor has been deleted")
                .build();
    }
    @GetMapping("/get/getDetail/{doctor_id}")//1s
    ApiResponse<DoctorDetailResponse> getDoctorById(@PathVariable("doctor_id") Integer doctorId){
        return ApiResponse.<DoctorDetailResponse>builder()
                .result(doctorService.getDoctorById(doctorId))
                .build();
    }

    //====

    @GetMapping("/myInfo")
    ApiResponse<DoctorDetailResponse> getDoctorInfo(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return ApiResponse.<DoctorDetailResponse>builder()
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

    @GetMapping("/get/doctorList")// 2s
    ApiResponse<List<DoctorListResponse>> getAllDoctors(){
        return ApiResponse.<List<DoctorListResponse>>builder()
                .result(doctorService.getAllDoctors())
                .build();
    }

    @GetMapping("/get/byName") //1s
    ApiResponse<List<DoctorListResponse>> getDoctorByName(@RequestParam("doctor_name") String doctorName){
        return ApiResponse.<List<DoctorListResponse>>builder()
                .result(doctorService.getDoctorByName(doctorName))
                .build();
    }

    @GetMapping("/get/byDepartment")// 2s
    ApiResponse<List<DoctorListResponse>> getDoctorByDepartment(@RequestParam("doctor_departmentId") Integer departmentId){
        return ApiResponse.<List<DoctorListResponse>>builder()
                .result(doctorService.getDoctorByDepartment(departmentId))
                .build();
    }
}
