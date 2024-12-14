package PBL6.example.UNIME.dto.response;

import PBL6.example.UNIME.enums.DayOfWeek;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorTimeworkResponse {
    Integer doctorTimeworkId;

    Integer doctorTimeworkYear;
    Integer weekOfYear;

    String dayOfWeek;
    LocalTime startTime;
    LocalTime endTime;

    Integer doctorId;
    String doctorName;
    String doctorTimeworkStatus;

}
