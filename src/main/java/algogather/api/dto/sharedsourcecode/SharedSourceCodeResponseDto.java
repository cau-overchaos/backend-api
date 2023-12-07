package algogather.api.dto.sharedsourcecode;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SharedSourceCodeResponseDto {
    private Long sharedSourceCodeId;
    private String sharedSourceCodeTitle;
    private Long problemDifficultyLevel;
    private Long pid;
    private String problemTitle;
    private String writerUserId;
    private String writerName;
    private Long programmingLanguageId;
    private String programmingLanguageName;
    private String sourceCodeText;
    private LocalDateTime createdAt;


    @Builder
    public SharedSourceCodeResponseDto(Long sharedSourceCodeId, String sharedSourceCodeTitle, Long pid, Long problemDifficultyLevel, String problemTitle, String writerUserId, String writerName, Long programmingLanguageId, String programmingLanguageName, String sourceCodeText, LocalDateTime createdAt) {
        this.sharedSourceCodeId = sharedSourceCodeId;
        this.sharedSourceCodeTitle = sharedSourceCodeTitle;
        this.pid = pid;
        this.problemDifficultyLevel = problemDifficultyLevel;
        this.problemTitle = problemTitle;
        this.writerUserId = writerUserId;
        this.writerName = writerName;
        this.programmingLanguageId = programmingLanguageId;
        this.programmingLanguageName = programmingLanguageName;
        this.sourceCodeText = sourceCodeText;
        this.createdAt = createdAt;
    }
}
