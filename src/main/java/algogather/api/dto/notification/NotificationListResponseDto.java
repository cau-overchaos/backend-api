package algogather.api.dto.notification;

import algogather.api.domain.notification.Notification;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class NotificationListResponseDto {

    private List<NotificationDto> notificationDtoList;

    public NotificationListResponseDto(List<Notification> notificationList) {
        this.notificationDtoList = notificationList.stream()
                .map(NotificationDto::new)
                .collect(Collectors.toList());
    }
}
