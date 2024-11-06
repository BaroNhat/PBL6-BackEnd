package PBL6.example.UNIME.controller;

import PBL6.example.UNIME.dto.request.PatientRequest;
import PBL6.example.UNIME.dto.response.ApiResponse;
import PBL6.example.UNIME.dto.response.PatientResponse;
import PBL6.example.UNIME.service.PatientService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8081", "https://unimehospital.vercel.app"})
@Slf4j
@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class PatientController {
    PatientService patientService;

    @PostMapping
    ApiResponse<PatientResponse> createPatient(@RequestBody @Valid PatientRequest request) {
        ApiResponse<PatientResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(patientService.createPatient(request));
        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<PatientResponse>> getAllPatients() {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<List<PatientResponse>>builder()
                .result(patientService.getAllPatients())
                .build();
    }

    @GetMapping("/myInfo")
    ApiResponse<PatientResponse> getMyInfo(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<PatientResponse>builder()
                .result(patientService.getMyInfo(authentication.getName()))
                .build();
    }
    @GetMapping("/{patient_id}")
    ApiResponse<PatientResponse> getPatient(@PathVariable("patient_id") Integer patient_id){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<PatientResponse>builder()
                .result(patientService.getPatientById(patient_id))
                .build();
    }

    @PutMapping("/update")
    ApiResponse<PatientResponse> updatePatient(@RequestBody PatientRequest request){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

//        log.info("username: {}", authentication.getName());
//        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<PatientResponse>builder()
                .result(patientService.updatePatient(username , request))
                .build();
    }

    @DeleteMapping("/{patient_id}")
    ApiResponse<String> deleteUser(@PathVariable Integer patient_id){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        patientService.deletePatient(patient_id);
        return ApiResponse.<String>builder()
                .result( "Patient has been deleted")
                .build();
    }
}
