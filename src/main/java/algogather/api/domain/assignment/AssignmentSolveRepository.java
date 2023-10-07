package algogather.api.domain.assignment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentSolveRepository extends JpaRepository<AssignmentSolve, Long> {
    boolean existsByUserIdAndAssignmentProblemId(Long userId, Long assignmentProblemId);
}
