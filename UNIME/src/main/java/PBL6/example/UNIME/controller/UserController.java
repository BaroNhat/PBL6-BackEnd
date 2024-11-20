package PBL6.example.UNIME.controller;

import PBL6.example.UNIME.dto.request.AuthenticationRequest;
import PBL6.example.UNIME.dto.request.PasswordRequest;
import PBL6.example.UNIME.dto.response.ApiResponse;
import PBL6.example.UNIME.dto.response.AuthenticationResponse;
import PBL6.example.UNIME.entity.User;
import PBL6.example.UNIME.exception.AppException;
import PBL6.example.UNIME.exception.ErrorCode;
import PBL6.example.UNIME.repository.UserRepository;
import PBL6.example.UNIME.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/password")
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;
    UserRepository userRepository;

    @PutMapping
    ApiResponse<String> updatePassword(@RequestBody PasswordRequest request) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return  ApiResponse.<String>builder()
                .result(userService.updatePassword(authentication.getName(),request))
                .build();
    }
}
