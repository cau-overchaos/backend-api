package algogather.api.service.programmingllanguage;

import algogather.api.domain.programminglanguage.ProgrammingLanguage;
import algogather.api.domain.programminglanguage.ProgrammingLanguageRepository;
import algogather.api.domain.programminglanguage.StudyRoomProgrammingLanguageRepository;
import algogather.api.dto.programminglanguage.ProgrammingLanguageListResponseDto;
import algogather.api.exception.programminglanguage.ProgrammingLanguageAndStudyRoomNotMatchingException;
import algogather.api.exception.programminglanguage.ProgrammingLanguageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgrammingLanguageService {
    private final ProgrammingLanguageRepository programmingLanguageRepository;
    private final StudyRoomProgrammingLanguageRepository studyRoomProgrammingLanguageRepository;
    public ProgrammingLanguageListResponseDto findAll() {
        List<ProgrammingLanguage> programmingLanguageList = programmingLanguageRepository.findAll();

        return new ProgrammingLanguageListResponseDto(programmingLanguageList);
    }

    public ProgrammingLanguage findById(Long programmingLanguageId) {

        return programmingLanguageRepository.findById(programmingLanguageId).orElseThrow(ProgrammingLanguageNotFoundException::new);
    }

    public void throwExceptionIfStudyRoomIdAndProgrammingLanguageIdNotMatching(Long studyRoomId, Long programmingLanguageId) {
        studyRoomProgrammingLanguageRepository.findByStudyRoomIdAndProgrammingLanguageId(studyRoomId, programmingLanguageId).orElseThrow(ProgrammingLanguageAndStudyRoomNotMatchingException::new);
    }

}
