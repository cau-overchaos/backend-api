package algogather.api.service.notification;

import algogather.api.domain.notification.Notification;
import algogather.api.domain.notification.NotificationRepository;
import algogather.api.domain.user.User;
import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.notification.NotificationDto;
import algogather.api.dto.notification.NotificationListResponseDto;
import algogather.api.dto.notification.NotificationReadRequestDto;
import algogather.api.exception.notification.NotificationNotFoundException;
import algogather.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final UserService userService;
    private final NotificationRepository notificationRepository;
    public Notification addNotification(String content, User user) {

        Notification newNotification = Notification.builder()
                .content(content)
                .isNew(true)
                .user(user)
                .build();

        Notification savedNotification = notificationRepository.save(newNotification);

        return savedNotification;
    }

    public NotificationListResponseDto getNotificationListResponseDto(UserAdapter userAdapter) {

        List<Notification> notificationList = notificationRepository.findByUserIdOOrderByIdDesc(userAdapter.getUser().getId());

        return new NotificationListResponseDto(notificationList);
    }

    public Boolean existsNewNotification(UserAdapter userAdapter) {
        return notificationRepository.existsByUserIdAndIsNewIsTrue(userAdapter.getUser().getId());
    }

    public NotificationDto readNotification(NotificationReadRequestDto notificationReadRequestDto, UserAdapter userAdapter) {
        Notification notification = findById(notificationReadRequestDto.getNotificationId());

        notification.changeIsNewToFalse();

        return NotificationDto
                .builder()
                .notification(notification)
                .build();
    }

    public Notification findById(Long notificationId) {
        return notificationRepository.findById(notificationId).orElseThrow(NotificationNotFoundException::new);
    }
}
