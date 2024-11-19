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
public class DoctorTimeworkUpdateRequest {

    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    Integer doctorTimeworkId;


    @NotEmpty(message = "MISSING_REQUIRED_FIELDS")
    String doctorTimeworkStatus;

}
