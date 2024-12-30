package PBL6.example.UNIME.controller;

import PBL6.example.UNIME.dto.response.ApiResponse;
import PBL6.example.UNIME.service.MailService;
import PBL6.example.UNIME.service.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    MailService mailService;

    @PostMapping("/sendOTP")
    ApiResponse<String> sendOtp(@RequestParam("mail") String mail) {
        return ApiResponse.<String>builder()
                .result(mailService.sendOtp(mail)).build();
    }

    @PostMapping("/sendPassword")
    ApiResponse<String> sendPassword(@RequestParam("mail") String mail) {
        return  ApiResponse.<String>builder()
                .result(mailService.sendPasswork(mail))
                .build();
    }
}
