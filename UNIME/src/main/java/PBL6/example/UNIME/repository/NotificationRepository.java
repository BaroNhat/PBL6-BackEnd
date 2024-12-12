package PBL6.example.UNIME.repository;

import PBL6.example.UNIME.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
