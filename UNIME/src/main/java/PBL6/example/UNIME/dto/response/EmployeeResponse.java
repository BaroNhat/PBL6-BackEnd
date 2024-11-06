package PBL6.example.UNIME.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeResponse {
    int employeeId;
    Integer userId;
    String employeeUsername;
    String employeePassword;
    String employeeEmail;
    String employeeName;
    String employeePhoneNumber;
    boolean employeeGender;
    String employeeStatus;
    Integer departmentId;
}
