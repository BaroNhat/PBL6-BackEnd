package PBL6.example.UNIME.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    Integer notificationId;

    @Column(name = "notification_message", nullable = true, columnDefinition = "TEXT")
    String notificationMessage;

    @Column(name = "recipient_id", nullable = true)
    Integer recipientId;

    @Column(name = "recipient_type", nullable = true)
    String recipientType;

    @Column(name = "related_object_type", nullable = true)
    String relatedObjectType;

    @Column(name = "related_object_id", nullable = false)
    Integer relatedObjectId;

    @Column(name = "create_time")
    LocalDateTime createTime = LocalDateTime.now();



}
