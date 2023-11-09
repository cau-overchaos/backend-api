package algogather.api.dto.sharedsourcecode;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class FeedbackListByLineNumberResponseDto {
    private Long sourceCodeId;
    private Long lineNumber;
    private List<FeedbackGroupByParentResponseDto> feedbackGroupByParentResponseDtoList;

    @Builder
    public FeedbackListByLineNumberResponseDto(Long sourceCodeId, Long lineNumber, List<FeedbackGroupByParentResponseDto> feedbackGroupByParentResponseDtoList) {
        this.sourceCodeId = sourceCodeId;
        this.lineNumber = lineNumber;
        this.feedbackGroupByParentResponseDtoList = feedbackGroupByParentResponseDtoList;
    }
}
