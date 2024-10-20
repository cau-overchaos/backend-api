package algogather.api.domain.notification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserIdAndIsNewIsTrueOrderByIdDesc(Long userId);

    Boolean existsByUserIdAndIsNewIsTrue(Long userId);
}
