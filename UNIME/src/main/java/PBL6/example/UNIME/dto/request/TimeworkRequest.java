package PBL6.example.UNIME.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeworkRequest {

    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    String dayOfWeek;

    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    String startTime;

    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    String endTime;
}
