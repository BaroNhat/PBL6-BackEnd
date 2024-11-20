package PBL6.example.UNIME.entity;


import PBL6.example.UNIME.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id", nullable = false, updatable = false)
    Integer appointmentId;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id")
    Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctortimework_id", referencedColumnName = "doctortimework_id")
    DoctorTimework doctortimework;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    Employee employee;

    @ManyToOne
    @JoinColumn(name = "doctorservice_id", referencedColumnName = "doctorservice_id")
    DoctorService doctorservice;

    @Column(name = "appointment_created_at", nullable = false, updatable = false)
    LocalDateTime appointmentCreatedAt = LocalDateTime.now();

    @Column(name = "appointment_status", nullable = false)
    String appointmentStatus = AppointmentStatus.Pending.name();

    @Column(name = "appointment_note")
    String appointmentNote;

}
