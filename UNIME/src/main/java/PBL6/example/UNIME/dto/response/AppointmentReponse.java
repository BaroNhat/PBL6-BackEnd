package PBL6.example.UNIME.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentReponse {

    Integer appointmentId ;

    String patientName;
    String doctorName;

    String dayOfWeek;
    String startTime;
    String endTime;

    String doctorserviceName;

    String appointmentCreatedAt ;
    String appointmentStatus ;

    Integer employeeId;
    String appointmentNote;

}
