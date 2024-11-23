package PBL6.example.UNIME.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordRequest {
    @NotEmpty(message = "MISSING_REQUIRED_FIELDS")
    String oldPassword;
    @NotEmpty(message = "MISSING_REQUIRED_FIELDS")
    @Size(min = 8, message = "INVALID_PASSWORD_FORMAT")
    String newPassword;
}
