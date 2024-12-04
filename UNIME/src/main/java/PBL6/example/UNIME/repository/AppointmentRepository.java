package PBL6.example.UNIME.repository;

import PBL6.example.UNIME.entity.Appointment;
import PBL6.example.UNIME.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    @Query("""
        SELECT 
            apt.appointmentId AS appointmentId,
            apt.patient.patientName AS patientName,
            apt.doctortimework.doctor.doctorName AS doctorName,
            apt.doctortimework.timeWork.dayOfWeek AS dayOfWeek,
            apt.doctortimework.timeWork.startTime AS startTime,
            apt.doctortimework.timeWork.endTime   AS endTime  ,
            apt.doctorservice.service.serviceName AS serviceName,
            apt.appointmentCreatedAt  AS appointmentCreatedAt,
            apt.appointmentStatus AS appointmentStatus,
            apt.employee.employeeId AS employeeId,
            apt.employee.employeeName AS employeeName,
            apt.appointmentNote AS appointmentNote
        FROM Appointment apt
        WHERE apt.doctortimework.doctor.department.departmentId = :departmentId
    """)
    List<Map<String,Object>> findByDepartment(@Param("departmentId") Integer departmentId);


    @Query("""
        SELECT 
            apt.appointmentId AS appointmentId,
            apt.patient.patientName AS patientName,
            apt.doctortimework.doctor.doctorName AS doctorName,
            apt.doctortimework.timeWork.dayOfWeek AS dayOfWeek,
            apt.doctortimework.timeWork.startTime AS startTime,
            apt.doctortimework.timeWork.endTime   AS endTime  ,
            apt.doctorservice.service.serviceName AS serviceName,
            apt.appointmentCreatedAt  AS appointmentCreatedAt,
            apt.appointmentStatus AS appointmentStatus,
            apt.employee.employeeId AS employeeId,
            apt.employee.employeeName AS employeeName,
            apt.appointmentNote AS appointmentNote
        FROM Appointment apt
        WHERE apt.doctortimework.doctor.doctorId = :doctorId
    """)
    List<Map<String,Object>> findByDoctor(@Param("doctorId") Integer doctorId);
}

