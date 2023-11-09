package algogather.api.dto.sharedsourcecode;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CountFeedbackByLineResponseDto {
    Long lineNumber;
    Long feedbackCount;

    @Builder
    public CountFeedbackByLineResponseDto(Long lineNumber, Long feedbackCount) {
        this.lineNumber = lineNumber;
        this.feedbackCount = feedbackCount;
    }
}
