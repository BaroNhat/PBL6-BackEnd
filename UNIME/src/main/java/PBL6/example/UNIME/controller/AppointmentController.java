package PBL6.example.UNIME.controller;

import PBL6.example.UNIME.dto.request.AppointmentCreateRequest;
import PBL6.example.UNIME.dto.request.AppointmentUpdateRequest;
import PBL6.example.UNIME.dto.response.ApiResponse;
import PBL6.example.UNIME.dto.response.AppointmentReponse;
import PBL6.example.UNIME.service.AppointmentService;
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
@RequestMapping("/appointments")
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping
    ApiResponse<String> createAppointment(@RequestBody AppointmentCreateRequest request){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return ApiResponse.<String>builder()
                .result(appointmentService.createAppointment(authentication.getName(), request))
                .build();
    }

    @PutMapping("/updateCompleted")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    ApiResponse<String> updateByDoctor(@RequestBody AppointmentUpdateRequest request){
        return ApiResponse.<String>builder()
                .result(appointmentService.updateByDoctor(request)).build();
    }
    @PutMapping("/updateCancelled")
    ApiResponse<String> updateByEmployee(@RequestBody AppointmentUpdateRequest request){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return ApiResponse.<String>builder()
                .result(appointmentService.updateByEmployee(authentication.getName(), request)).build();
    }

    @GetMapping("/getByDepartment")
    ApiResponse<List<AppointmentReponse>> getAppointmentsByDepartment(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return ApiResponse.<List<AppointmentReponse>>builder()
                .result(appointmentService.getAppointmentsByDepartment(authentication.getName()))
                .build();
    }

    @GetMapping("/getByDoctor")
    ApiResponse<List<AppointmentReponse>> getAppointmentsByDoctorId(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return ApiResponse.<List<AppointmentReponse>>builder()
                .result(appointmentService.getAppointmentsByDoctorId(authentication.getName()))
                .build();
    }
}
