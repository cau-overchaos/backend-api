package algogather.api.domain.sharedsourcecode;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SharedSourceCodeRepository extends JpaRepository<SharedSourceCode, Long> {
    List<SharedSourceCode> findByStudyRoomIdOrderByCreatedAtDesc(Long studyRoomId);

    Optional<SharedSourceCode> findByIdAndStudyRoomId(Long sharedSourceCodeId, Long studyRoomId);
}
