package PBL6.example.UNIME.repository;

import PBL6.example.UNIME.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
}

