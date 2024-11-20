package PBL6.example.UNIME.controller;

import PBL6.example.UNIME.dto.request.AppointmentRequest;
import PBL6.example.UNIME.dto.response.ApiResponse;
import PBL6.example.UNIME.service.AppointmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping
    ApiResponse<String> createAppointment(@RequestBody AppointmentRequest request){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return ApiResponse.<String>builder()
                .result(appointmentService.createAppointment(authentication.getName(), request))
                .build();
    }
}
