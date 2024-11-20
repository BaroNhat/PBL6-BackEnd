package PBL6.example.UNIME.dto.request;

import PBL6.example.UNIME.enums.AppointmentStatus;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentRequest {

    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    Integer patientId;

    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    Integer doctortimeworkId;

    Integer employeeId;

    @NotNull(message = "MISSING_REQUIRED_FIELDS")
    Integer doctorserviceId;

    String appointmentCreatedAt ;

    String appointmentStatus ;

    String appointmentNote;
}
