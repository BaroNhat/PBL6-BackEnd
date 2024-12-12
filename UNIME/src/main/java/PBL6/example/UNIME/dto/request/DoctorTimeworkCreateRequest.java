package PBL6.example.UNIME.dto.request;

import PBL6.example.UNIME.enums.DoctorTimeworkStatus;
import PBL6.example.UNIME.exception.AppException;
import PBL6.example.UNIME.exception.ErrorCode;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorTimeworkCreateRequest {
    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    Integer doctorTimeworkYear;

    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    Integer weekOfYear;

    //
    @NotEmpty(message = "MISSING_REQUIRED_FIELDS")
    Integer timework_id;

//    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    Integer doctorId;


    @NotEmpty(message = "MISSING_REQUIRED_FIELDS")
    String doctorTimeworkStatus;



    public void validateRequest() {

        log.info("doctortimeworkStatus: {}", doctorTimeworkStatus);
        log.info("weekOfYear: {}", weekOfYear);
        // Kiểm tra CHECK_DOCTOR_TIMEWORK_STATUS
        DoctorTimeworkStatus.contains(doctorTimeworkStatus);

        // Kiểm tra CHECK_WEEK_OF_YEAR
        if (weekOfYear == null || weekOfYear < 0 || weekOfYear >= 54) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }
    }


}
