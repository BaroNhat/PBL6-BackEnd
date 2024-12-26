package PBL6.example.UNIME.repository;

import PBL6.example.UNIME.entity.Patient;
import PBL6.example.UNIME.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Query("SELECT p " +
            "FROM Patient p JOIN FETCH p.patientUser u " +
            "WHERE u.username = :username")
    Optional<Patient> findBypatientUserUsername(@Param("username")String username);

    @Query("SELECT p " +
            "FROM Patient p JOIN FETCH p.patientUser ")
    List<Patient> findAll();


}
