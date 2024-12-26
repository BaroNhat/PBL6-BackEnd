package PBL6.example.UNIME.repository;

import PBL6.example.UNIME.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    @Query("SELECT d " +
            "FROM Doctor d JOIN FETCH d.doctorUserId JOIN FETCH d.department JOIN FETCH  d.doctorDetail " +
            "WHERE d.doctorName like %:doctorName%")
    List<Doctor> findByDoctorNameContaining(@Param("doctorName")String doctorName);

    @Query("SELECT d " +
            "FROM Doctor d  " +
            "WHERE d.department.departmentId = :departmentId")
    List<Doctor> findBydepartment(@Param("departmentId")Integer departmentId);

    @Query("SELECT d " +
            "FROM Doctor d JOIN FETCH d.doctorUserId JOIN FETCH d.department JOIN FETCH  d.doctorDetail " +
            "WHERE d.doctorId = :id")
    Optional<Doctor>  findDoctorById(@Param("id") Integer id);

    @Query("SELECT d " +
            "FROM Doctor d JOIN FETCH d.doctorUserId u JOIN FETCH d.department JOIN FETCH  d.doctorDetail " +
            "WHERE u.username = :username")
    Optional<Doctor> findDetailByUsername(@Param("username") String username);

    @Query("SELECT d " +
            "FROM Doctor d " +
            "WHERE d.doctorUserId.username = :username")
    Optional<Doctor> findByUsername(String username);

    @Query("SELECT d " +
            "FROM Doctor d JOIN FETCH d.doctorUserId JOIN FETCH d.department JOIN FETCH  d.doctorDetail ")
    List<Doctor> findAll();

}
