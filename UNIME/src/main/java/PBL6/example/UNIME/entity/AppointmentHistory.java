package PBL6.example.UNIME.entity;

import PBL6.example.UNIME.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "appointment_history")
public class AppointmentHistory {

    @Id
    @Column(name = "appointment_id")
    private Integer appointmentId;

    @Column(name = "patient_username", nullable = false)
    private String patientUsername;

    @Column(name = "doctor_username", nullable = false)
    private String doctorUsername;

    @Column(name = "department_id", nullable = false)
    private Integer departmentId;

    @Column(name = "patient_name", nullable = false)
    private String patientName;

    @Column(name = "doctor_name", nullable = false)
    private String doctorName;

    @Column(name = "doctortimework_year", nullable = false)
    private Integer doctorTimeWorkYear;

    @Column(name = "week_of_year", nullable = false)
    private Integer weekOfYear;

    @Column(name = "day_of_week", nullable = false)
    private String dayOfWeek;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "service_name", nullable = false)
    private String serviceName;

    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "appointment_status", nullable = false)
    private String appointmentStatus;

    @Column(name = "appointment_note")
    private String appointmentNote;
}
