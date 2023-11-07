package algogather.api.dto.sharedsourcecode;

import lombok.Getter;

import java.util.List;

@Getter
public class FeedbackListResponseDto {
    FeedbackResponseDto parentFeedbackResponseDto;
    List<FeedbackResponseDto> childrenFeedbackResponseDtoList;
}
