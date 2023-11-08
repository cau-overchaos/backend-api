package algogather.api.dto.sharedsourcecode;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class FeedbackGroupByParentResponseDto {
    private Long parentId;
    private FeedbackResponseDto parentFeedbackResponseDto;
    private List<FeedbackResponseDto> childrenFeedbackResponseDtoList;

    @Builder
    public FeedbackGroupByParentResponseDto(Long parentId, FeedbackResponseDto parentFeedbackResponseDto, List<FeedbackResponseDto> childrenFeedbackResponseDtoList) {
        this.parentId = parentId;
        this.parentFeedbackResponseDto = parentFeedbackResponseDto;
        this.childrenFeedbackResponseDtoList = childrenFeedbackResponseDtoList;
    }
}
