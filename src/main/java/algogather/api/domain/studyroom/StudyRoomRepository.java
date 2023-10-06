package algogather.api.domain.studyroom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudyRoomRepository extends JpaRepository<StudyRoom, Long> {

    @Query("SELECT sr " +
            "FROM StudyRoom sr " +
            "JOIN FETCH UserStudyRoom usr ON sr.id = usr.studyRoom.id " +
            "WHERE usr.user.id = :id")
    List<StudyRoom> findStudyRoomsByUserId(Long id);
}
