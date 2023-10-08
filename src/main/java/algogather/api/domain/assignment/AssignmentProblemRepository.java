package algogather.api.domain.assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssignmentProblemRepository extends JpaRepository<AssignmentProblem, Long> {

    List<AssignmentProblem> findByStudyRoomId(Long StudyRoomId);
}
