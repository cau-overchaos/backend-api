package algogather.api.domain.studyroom;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserStudyRoomRepository extends JpaRepository<UserStudyRoom, Long> {
    Optional<UserStudyRoom> findByUserIdAndStudyRoomId(Long userId, Long studyRoomId);
}
