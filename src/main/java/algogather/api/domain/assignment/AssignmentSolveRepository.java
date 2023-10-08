package algogather.api.domain.assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AssignmentSolveRepository extends JpaRepository<AssignmentSolve, Long> {
    Optional<AssignmentSolve> findByUserIdAndAssignmentProblemId(Long userId, Long assignmentProblemId);

    List<AssignmentSolve> findByAssignmentProblemId(Long assignmentProblemId);
}
