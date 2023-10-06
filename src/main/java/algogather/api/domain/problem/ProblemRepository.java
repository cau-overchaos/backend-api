package algogather.api.domain.problem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    Optional<Problem> findByPidAndProvider(Long pid, ProblemProvider provider);
}
