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
        private Long sharedSourceCodeId;
        private String sharedSourceCodeTitle;
        private Long problemDifficultyLevel;
        private String problemTitle;
        private String writerUserId;
        private String writerName;
        private Long programmingLanguageId;
        private String programmingLanguageName;
        private LocalDateTime createdAt;

        public SharedSourceCodeInfoDto(SharedSourceCode sourceCode) {
            this.sharedSourceCodeId = sourceCode.getId();
            this.sharedSourceCodeTitle = sourceCode.getTitle();
            this.problemDifficultyLevel = sourceCode.getProblem().getDifficulty().getLevel();
            this.problemTitle = sourceCode.getProblem().getTitle();
            this.writerUserId = sourceCode.getUser().getUserId();
            this.writerName = sourceCode.getUser().getName();
            this.programmingLanguageId = sourceCode.getProgrammingLanguage().getId();
            this.programmingLanguageName = sourceCode.getProgrammingLanguage().getName();
            this.createdAt = sourceCode.getCreatedAt();
        }
    }
}
