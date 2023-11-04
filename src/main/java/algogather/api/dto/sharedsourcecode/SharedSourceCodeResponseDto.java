package algogather.api.dto.sharedsourcecode;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SharedSourceCodeResponseDto {
    private Long id;
    private String sharedSourceCodeTitle;
    private Long problemDifficultyLevel;
    private String problemTitle;
    private String writerName;
    private String programmingLanguage;
    private String sourceCodeText;
    private LocalDateTime createdAt;


    @Builder
    public SharedSourceCodeResponseDto(Long id, String sharedSourceCodeTitle, Long problemDifficultyLevel, String problemTitle, String writerName, String programmingLanguage, String sourceCodeText, LocalDateTime createdAt) {
        this.id = id;
        this.sharedSourceCodeTitle = sharedSourceCodeTitle;
        this.problemDifficultyLevel = problemDifficultyLevel;
        this.problemTitle = problemTitle;
        this.writerName = writerName;
        this.programmingLanguage = programmingLanguage;
        this.sourceCodeText = sourceCodeText;
        this.createdAt = createdAt;
    }
}
