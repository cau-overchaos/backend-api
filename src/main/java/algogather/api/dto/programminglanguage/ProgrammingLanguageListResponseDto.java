package algogather.api.dto.programminglanguage;


import algogather.api.domain.programminglanguage.ProgrammingLanguage;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProgrammingLanguageListResponseDto {

    private final List<ProgrammingLanguageResponseDto> programmingLanguageResponseDtoList;

    public ProgrammingLanguageListResponseDto(List<ProgrammingLanguage> programmingLanguageList) {
        this.programmingLanguageResponseDtoList = programmingLanguageList.stream()
                .map(programmingLanguage -> new ProgrammingLanguageResponseDto(programmingLanguage))
                .collect(Collectors.toList());
    }

    @Getter
    static class ProgrammingLanguageResponseDto {
        private final Long id;
        private final String name;

        public ProgrammingLanguageResponseDto(ProgrammingLanguage programmingLanguage) {
            this.id = programmingLanguage.getId();
            this.name = programmingLanguage.getName();
        }
    }

}
