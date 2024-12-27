package PBL6.example.UNIME.repository;

import PBL6.example.UNIME.entity.AppointmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentHistoryRepository extends JpaRepository<AppointmentHistory, Integer> {
    List<AppointmentHistory> findBypatientUsername(String patientUsername);
    List<AppointmentHistory> findBydoctorUsername(String doctorUsername);
    List<AppointmentHistory> findBydepartmentId(Integer departmentId);
}
