package algogather.api.domain.recruit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitPostRepository extends JpaRepository<RecruitPost, Long> {
    List<RecruitPost> findAllByOrderByIdDesc();
}
