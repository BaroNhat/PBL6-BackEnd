package PBL6.example.UNIME.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "patient")
public class Patient{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    Integer patientId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_userId", referencedColumnName = "user_id", nullable = false, unique = true)
    User patientUser;

    @Column(name = "patient_image", nullable = false, length = 255)
    String patientImage;

    @Column(name = "patient_name", nullable = false, length = 255)
    String patientName;

    @Column(name = "patient_address", length = 255)
    String patientAddress;

    @Column(name = "patient_phonenumber", nullable = false, length = 20)
    String patientPhoneNumber;

    @Column(name = "patient_gender", nullable = false)
    boolean patientGender;

    @Column(name = "patient_date_of_birth")
    LocalDate patientDateOfBirth;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments;
}
