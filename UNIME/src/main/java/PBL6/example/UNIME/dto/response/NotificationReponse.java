package PBL6.example.UNIME.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationReponse {

    Integer notificationId;
    String notificationMessage;
    Integer recipientId;
    String recipientType;
    String relatedObjectType;
    String relatedObjectId;
    LocalDateTime createTime;

}
