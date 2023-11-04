package algogather.api.domain.sharedsourcecode;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SharedSourceCodeRepository extends JpaRepository<SharedSourceCode, Long> {
    List<SharedSourceCode> findByStudyRoomIdOrderByCreatedAt(Long studyRoomId);
}
