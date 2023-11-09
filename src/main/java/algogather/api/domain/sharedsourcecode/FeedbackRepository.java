package algogather.api.domain.sharedsourcecode;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findBySharedSourceCodeIdAndSourceCodeLineNumberAndReplyParentFeedbackIsNull(Long sharedSourceCodeId, Long sourceCodeLineNumber); // 해당 소스코드와 줄 번호에 해당하는 루트 피드백들만 가져온다.

    List<Feedback> findByReplyParentFeedbackId(Long replyParentFeedbackId);

    Long countBySharedSourceCodeIdAndSourceCodeLineNumberAndIsDeletedIsFalse(Long sharedSourceCodeId, Long sourceCodeLineNumber);
}
