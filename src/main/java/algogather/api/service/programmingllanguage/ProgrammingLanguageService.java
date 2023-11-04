package algogather.api.service.programmingllanguage;

import algogather.api.domain.programminglanguage.ProgrammingLanguage;
import algogather.api.domain.programminglanguage.ProgrammingLanguageRepository;
import algogather.api.dto.programminglanguage.ProgrammingLanguageListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgrammingLanguageService {
    private final ProgrammingLanguageRepository programmingLanguageRepository;

    public ProgrammingLanguageListResponseDto findAll() {
        List<ProgrammingLanguage> programmingLanguageList = programmingLanguageRepository.findAll();

        return new ProgrammingLanguageListResponseDto(programmingLanguageList);
    }
}
