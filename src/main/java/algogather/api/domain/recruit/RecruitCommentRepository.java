package algogather.api.domain.recruit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitCommentRepository extends JpaRepository<RecruitComment, Long> {
    List<RecruitComment> findByRecruitPostId(Long recruitPostId);
}
