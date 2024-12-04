package PBL6.example.UNIME.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorDetailResponse {

    Integer doctorId;
    String doctorImage;
    String doctorName;
    String doctorAddress;
    String doctorPhoneNumber;
    Boolean doctorGender;
    LocalDate doctorDateOfBirth;
    String doctorDescription;

    String doctorEmail;

    String departmentName;

    String doctordetailInformation;
    String doctordetailExperience;
    String doctordetailAwardRecognization;
}
