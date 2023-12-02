package algogather.api.service.notification;

import algogather.api.domain.notification.Notification;
import algogather.api.domain.notification.NotificationRepository;
import algogather.api.domain.user.User;
import algogather.api.domain.user.UserAdapter;
import algogather.api.domain.user.UserRepository;
import algogather.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final UserService userService;
    private final NotificationRepository notificationRepository;
    public Notification addNotification(String content, UserAdapter userAdapter) {

        User user = userService.findById(userAdapter.getUser().getId());

        Notification newNotification = Notification.builder()
                .content(content)
                .isNew(true)
                .user(user)
                .build();

        Notification savedNotification = notificationRepository.save(newNotification);

        return savedNotification;
    }
}
