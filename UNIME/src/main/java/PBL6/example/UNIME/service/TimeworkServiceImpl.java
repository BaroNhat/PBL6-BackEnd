package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.response.TimeworkResponse;
import PBL6.example.UNIME.entity.Timework;
import PBL6.example.UNIME.exception.AppException;
import PBL6.example.UNIME.exception.ErrorCode;
import PBL6.example.UNIME.repository.TimeworkRespository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class TimeworkServiceImpl implements TimeworkService {

    TimeworkRespository timeworkRespository;

    public List<TimeworkResponse> getAllTimeworks() {
        List<Timework> timeworks = timeworkRespository.findAll();
        return timeworks.stream()
                .map(this::mapToResponse)
                .toList();
    }

    //============
    private TimeworkResponse mapToResponse(Timework timework) {
        return new TimeworkResponse(
                timework.getId(),
                timework.getDayOfWeek().toString(),
                timework.getStartTime().toString(),
                timework.getEndTime().toString()
        );
    }

}
