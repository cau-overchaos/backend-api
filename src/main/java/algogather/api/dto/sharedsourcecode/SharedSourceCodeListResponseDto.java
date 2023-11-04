package algogather.api.dto.sharedsourcecode;

import algogather.api.domain.sharedsourcecode.SharedSourceCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SharedSourceCodeListResponseDto {

    private List<SharedSourceCodeInfoDto> sharedSourceCodeInfoDtoList;

    public SharedSourceCodeListResponseDto(List<SharedSourceCode> sharedSourceCodeList) {
        this.sharedSourceCodeInfoDtoList = sharedSourceCodeList.stream()
                .map(SharedSourceCodeInfoDto::new).collect(Collectors.toList());
    }

    @Getter
    static class SharedSourceCodeInfoDto {
        private Long id;
        private String sharedSourceCodeTitle;
        private Long problemDifficultyLevel;
        private String problemTitle;
        private String writerName;
        private String programmingLanguage;
        private LocalDateTime createdAt;

        public SharedSourceCodeInfoDto(SharedSourceCode sourceCode) {
            this.id = sourceCode.getId();
            this.sharedSourceCodeTitle = sourceCode.getTitle();
            this.problemDifficultyLevel = sourceCode.getProblem().getDifficulty().getLevel();
            this.problemTitle = sourceCode.getProblem().getTitle();
            this.writerName = sourceCode.getUser().getName();
            this.programmingLanguage = sourceCode.getProgrammingLanguage().getName();
            this.createdAt = sourceCode.getCreatedAt();
        }
    }
}
