package algogather.api.domain.assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssignmentSolveRepository extends JpaRepository<AssignmentSolve, Long> {
    boolean existsByUserIdAndAssignmentProblemId(Long userId, Long assignmentProblemId);

    List<AssignmentSolve> findByAssignmentProblemId(Long assignmentProblemId);
}
