package PBL6.example.UNIME.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentUpdateRequest {

    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    Integer appointmentId;

    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    String appointmentStatus ;

    String appointmentNote;
}
