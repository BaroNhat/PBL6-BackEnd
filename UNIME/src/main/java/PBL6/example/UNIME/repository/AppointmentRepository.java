package PBL6.example.UNIME.repository;

import PBL6.example.UNIME.entity.Appointment;
import PBL6.example.UNIME.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    @Query("""
        SELECT apt
        FROM Appointment apt
        WHERE apt.doctortimework.doctor.department.departmentId = :departmentId
    """)
    List<Appointment> findByDepartment(@Param("departmentId") Integer departmentId);


    @Query("""
        SELECT apt
        FROM Appointment apt
        WHERE apt.doctortimework.doctor.doctorId = :doctorId
    """)
    List<Appointment> findByDoctor(@Param("doctorId") Integer doctorId);
}

