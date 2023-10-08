package algogather.api.domain.studyroom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import algogather.api.domain.user.User;


import java.util.List;
import java.util.Optional;

public interface UserStudyRoomRepository extends JpaRepository<UserStudyRoom, Long> {
    Optional<UserStudyRoom> findByUserIdAndStudyRoomId(Long userId, Long studyRoomId);
    @Query("SELECT u " +
            "FROM UserStudyRoom usr " +
            "JOIN FETCH User u ON usr.user.id = u.id " +
            "WHERE usr.studyRoom.id = :studyRoomId")
    List<User> findUserByStudyRoomId(Long studyRoomId);

}
