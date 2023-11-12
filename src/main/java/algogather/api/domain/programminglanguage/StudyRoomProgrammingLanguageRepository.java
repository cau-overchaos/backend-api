package algogather.api.domain.programminglanguage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudyRoomProgrammingLanguageRepository extends JpaRepository<StudyRoomProgrammingLanguage, Long>{

    @Query("SELECT pl " +
            "FROM StudyRoomProgrammingLanguage srpl " +
            "JOIN ProgrammingLanguage pl ON srpl.programmingLanguage.id = pl.id " +
            "WHERE srpl.studyRoom.id = :studyRoomId")
    List<ProgrammingLanguage> findProgrammingLanguagesByStudyRoomId(Long studyRoomId);

    Optional<StudyRoomProgrammingLanguage> findByStudyRoomIdAndProgrammingLanguageId(Long studyRoomId, Long programmingLanugeId);
}
