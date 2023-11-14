package algogather.api.dto.programminglanguage;


import algogather.api.domain.programminglanguage.ProgrammingLanguage;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProgrammingLanguageListResponseDto {

    private List<ProgrammingLanguageDto> programmingLanguageResponseDtoList;

    public ProgrammingLanguageListResponseDto(List<ProgrammingLanguage> programmingLanguageList) {
        this.programmingLanguageResponseDtoList = programmingLanguageList.stream()
                .map(programmingLanguage -> new ProgrammingLanguageDto(programmingLanguage)).collect(Collectors.toList());
    }

    @Getter
    static class ProgrammingLanguageDto {
        private Long id;
        private String name;

        public ProgrammingLanguageDto(ProgrammingLanguage programmingLanguage) {
            this.id = programmingLanguage.getId();
            this.name = programmingLanguage.getName();
        }
    }

}
