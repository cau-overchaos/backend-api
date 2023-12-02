package algogather.api.dto.notification;

import algogather.api.domain.notification.Notification;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NotificationDto {
    private Long id;
    private String content;
    private Boolean isNew;
    private LocalDateTime createdAt;

    @Builder
    public NotificationDto(Notification notification) {
        this.id = notification.getId();
        this.content = notification.getContent();
        this.isNew = notification.getIsNew();
        this.createdAt = notification.getCreatedAt();
    }
}
