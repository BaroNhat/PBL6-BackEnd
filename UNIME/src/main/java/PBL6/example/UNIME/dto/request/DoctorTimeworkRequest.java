package PBL6.example.UNIME.dto.request;

import PBL6.example.UNIME.enums.DayOfWeek;
import PBL6.example.UNIME.enums.DoctorTimeworkStatus;
import PBL6.example.UNIME.exception.AppException;
import PBL6.example.UNIME.exception.ErrorCode;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorTimeworkRequest {

    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    Integer doctorTimeworkYear;

    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    Integer weekOfYear;

    //
    @NotEmpty(message = "MISSING_REQUIRED_FIELDS")
    String dayOfWeek;

    @NotEmpty(message = "MISSING_REQUIRED_FIELDS")
    String startTime;

    @NotEmpty(message = "MISSING_REQUIRED_FIELDS")
    String endTime;
    //

//    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    Integer doctorId;


    @NotEmpty(message = "MISSING_REQUIRED_FIELDS")
    String doctorTimeworkStatus;



    public void validateRequest() {

        log.info("doctortimeworkStatus: {}", doctorTimeworkStatus);
        log.info("weekOfYear: {}", weekOfYear);
        // Kiểm tra CHECK_DOCTOR_TIMEWORK_STATUS
        if (!(DoctorTimeworkStatus.Available.name().equals(doctorTimeworkStatus)
                || DoctorTimeworkStatus.Busy.name().equals(doctorTimeworkStatus))) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }

        // Kiểm tra CHECK_WEEK_OF_YEAR
        if (weekOfYear == null || weekOfYear < 0 || weekOfYear >= 54) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }
    }


}
