package PBL6.example.UNIME.repository;

import PBL6.example.UNIME.entity.DoctorService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorServiceRepository extends JpaRepository <DoctorService, Integer> {
//    List<DoctorService> findBydoctor(Integer doctorId);
//    DoctorService findBydo(Integer doctorId, Integer serviceId);
}
