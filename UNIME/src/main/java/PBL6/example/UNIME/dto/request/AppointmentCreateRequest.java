package PBL6.example.UNIME.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentCreateRequest {

    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    Integer doctortimeworkId;

    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    Integer doctorId;

    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    Integer serviceId;
}
