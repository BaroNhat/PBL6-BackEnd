package PBL6.example.UNIME.repository;

import PBL6.example.UNIME.entity.Appointment;
import PBL6.example.UNIME.entity.Doctor;
import PBL6.example.UNIME.entity.DoctorService;
import PBL6.example.UNIME.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorServiceRepository extends JpaRepository <DoctorService, Integer> {
    @Query("SELECT ds " +
            "FROM DoctorService ds JOIN FETCH ds.service s JOIN FETCH ds.doctor d JOIN FETCH s.department " +
            "WHERE d.doctorId =:doctor_id")
    List<DoctorService> findBydoctor(@Param("doctor_id") Integer doctorId);

    @Query("SELECT ds " +
            "FROM DoctorService ds JOIN FETCH ds.service s JOIN FETCH ds.doctor d JOIN FETCH  d.department JOIN FETCH  d.doctorUserId  JOIN FETCH d.doctorDetail " +
            "WHERE s.serviceId = :service_id ")
    List<DoctorService> findByservice(@Param("service_id") Integer serviceId);


    @Query("SELECT ds " +
            "FROM DoctorService ds JOIN FETCH ds.service s JOIN FETCH ds.doctor d " +
            "WHERE s.serviceId = :service_id AND d.doctorId =:doctor_id")
    DoctorService findByDoctorAndService(@Param("doctor_id") Integer doctorId,
                                         @Param("service_id") Integer serviceId);

}
