package PBL6.example.UNIME.repository;

import PBL6.example.UNIME.entity.Appointment;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    @Query(" SELECT a " +
            "FROM Appointment a " +
            "JOIN FETCH a.doctorservice ds " +
            "JOIN FETCH ds.service s " +
            "JOIN FETCH ds.doctor d " +
            "JOIN FETCH a.patient " +
            "JOIN FETCH a.doctortimework tw " +
            "JOIN FETCH tw.timeWork t " +
            " WHERE t.dayOfWeek = :dayOfWeek " +
            "AND a.doctortimework.weekOfYear = :week " +
            "AND a.doctortimework.year = :year")
    List<Appointment> findAppointmentByDay(@Param("week") int week,
                                           @Param("year") int year,
                                           @Param("dayOfWeek") String dayOfWeek);

    @Query(" SELECT a " +
            "FROM Appointment a " +
            "JOIN FETCH a.doctorservice ds " +
            "JOIN FETCH ds.service s " +
            "JOIN FETCH ds.doctor d " +
            "JOIN FETCH a.patient " +
            "JOIN FETCH a.doctortimework tw " +
            "JOIN FETCH tw.timeWork " +
            "WHERE s.department.departmentId = :departmentId ")
    List<Appointment> findByDepartment(@Param("departmentId") Integer departmentId);


    @Query(" SELECT a " +
            "FROM Appointment a " +
            "JOIN FETCH a.doctorservice ds " +
            "JOIN FETCH ds.service s " +
            "JOIN FETCH ds.doctor d " +
            "JOIN FETCH a.patient " +
            "JOIN FETCH a.doctortimework tw " +
            "JOIN FETCH tw.timeWork " +
            "WHERE d.doctorUserId.username =:username ")
    List<Appointment> findByDoctor(@Param("username") String username );

    @Query(" SELECT a " +
            "FROM Appointment a " +
            "JOIN FETCH a.doctorservice ds " +
            "JOIN FETCH ds.service s " +
            "JOIN FETCH ds.doctor d " +
            "JOIN FETCH a.patient p " +
            "JOIN FETCH p.patientUser u "+
            "JOIN FETCH a.doctortimework tw " +
            "JOIN FETCH tw.timeWork " +
            "WHERE u.username =:username ")
    List<Appointment> findByPatient(@Param("username") String username );

    @Query("""
        SELECT a
            FROM Appointment a
            JOIN FETCH a.doctortimework tw
            JOIN FETCH tw.timeWork
            JOIN FETCH a.patient
            JOIN FETCH a.doctorservice ds
            JOIN FETCH ds.service
            JOIN FETCH ds.doctor d
            JOIN FETCH d.doctorUserId
    """)
    List<Appointment> findAll();

    @Query("""
            SELECT a
            FROM Appointment a
            JOIN FETCH a.doctortimework tw
            JOIN FETCH a.patient
            JOIN FETCH a.doctorservice ds
            JOIN FETCH ds.service
            JOIN FETCH ds.doctor d
            JOIN FETCH d.doctorUserId
            WHERE a.appointmentId =:id
        """)
    Optional<Appointment> findById(@Param("id") Integer id);

    @Query("SELECT a " +
            "FROM Appointment a JOIN FETCH a.doctorservice " +
            "WHERE a.doctorservice.doctorserviceId = :doctorServiceId ")
    List<Appointment> findByDoctorService (@Param("doctorServiceId") Integer doctorServiceId);
}

