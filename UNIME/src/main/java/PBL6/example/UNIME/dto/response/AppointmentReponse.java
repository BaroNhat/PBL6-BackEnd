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

    Integer year;
    Integer weekOfYear;
    String dayOfWeek;
    String startTime;
    String endTime;

    String serviceName;

    String appointmentStatus ;

    String employeeId;
    String employeeName;
    String appointmentNote;

}
