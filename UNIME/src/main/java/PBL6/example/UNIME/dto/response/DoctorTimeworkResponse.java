package PBL6.example.UNIME.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;


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
    String startTime;
    String endTime;

    Integer doctorId;
    String doctorName;
    String doctorTimeworkStatus;

}
