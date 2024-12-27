package PBL6.example.UNIME.controller;

import PBL6.example.UNIME.dto.response.ApiResponse;
import PBL6.example.UNIME.dto.response.AppointmentReponse;
import PBL6.example.UNIME.service.AppointmentHistoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/appointmentHistories")
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class AppointmentHistoryController {

    AppointmentHistoryService appointmentHistoryService;

    @GetMapping("/getByDepartment")
    ApiResponse<List<AppointmentReponse>> getAppointmentsByDepartment(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return ApiResponse.<List<AppointmentReponse>>builder()
                .result(appointmentHistoryService.getByDepartment(authentication.getName()))
                .build();
    }

    @GetMapping("/getByDoctor")
    ApiResponse<List<AppointmentReponse>> getAppointmentsByDoctorId(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return ApiResponse.<List<AppointmentReponse>>builder()
                .result(appointmentHistoryService.getByDoctor(authentication.getName()))
                .build();
    }

    @GetMapping("/getByPatient")
    ApiResponse<List<AppointmentReponse>> getAppointmentsByPatient(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return ApiResponse.<List<AppointmentReponse>>builder()
                .result(appointmentHistoryService.getByPatient(authentication.getName()))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAll")
    ApiResponse<List<AppointmentReponse>> getAppointments(){
        return ApiResponse.<List<AppointmentReponse>>builder()
                .result(appointmentHistoryService.getAllAppointments())
                .build();
    }

}
