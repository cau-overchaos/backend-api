package algogather.api.service.notification;

import algogather.api.domain.notification.Notification;
import algogather.api.domain.notification.NotificationRepository;
import algogather.api.domain.user.User;
import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.notification.NotificationDto;
import algogather.api.dto.notification.NotificationListResponseDto;
import algogather.api.dto.notification.NotificationReadRequestDto;
import algogather.api.exception.notification.NotNotificationReaderException;
import algogather.api.exception.notification.NotificationNotFoundException;
import algogather.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

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

        List<Notification> notificationList = notificationRepository.findByUserIdOrderByIdDescAndIsNewIsTrue(userAdapter.getUser().getId()); // 새로운 알림 목록만 전송

        return new NotificationListResponseDto(notificationList);
    }

    public Boolean existsNewNotification(UserAdapter userAdapter) {
        return notificationRepository.existsByUserIdAndIsNewIsTrue(userAdapter.getUser().getId());
    }

    @Transactional
    public NotificationDto readNotification(NotificationReadRequestDto notificationReadRequestDto, UserAdapter userAdapter) {
        Notification notification = findById(notificationReadRequestDto.getNotificationId());

        if(!Objects.equals(userAdapter.getUser().getId(), notification.getUser().getId())) { // 해당 알림의 주인만 알림을 읽을 수 있음
            throw new NotNotificationReaderException();
        }

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
