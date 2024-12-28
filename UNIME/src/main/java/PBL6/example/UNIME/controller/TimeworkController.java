package PBL6.example.UNIME.controller;

import PBL6.example.UNIME.dto.response.ApiResponse;
import PBL6.example.UNIME.dto.response.TimeworkResponse;
import PBL6.example.UNIME.service.TimeworkService;
import PBL6.example.UNIME.service.TimeworkServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/timeworks")
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class TimeworkController {
    TimeworkService timeworkService;


    @GetMapping("/get/timewordList")
    ApiResponse<List<TimeworkResponse>> getAllTimeworks() {
        return ApiResponse.<List<TimeworkResponse>>builder()
                .result(timeworkService.getAllTimeworks())
                .build();

    }

}
