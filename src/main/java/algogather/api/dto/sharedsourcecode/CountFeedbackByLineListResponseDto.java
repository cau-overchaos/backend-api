package algogather.api.dto.sharedsourcecode;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CountFeedbackByLineListResponseDto {
    Long sharedSourceCodeId;
    List<CountFeedbackByLineResponseDto> countFeedbackByLineResponseDtoList;

    @Builder
    public CountFeedbackByLineListResponseDto(Long sharedSourceCodeId, List<CountFeedbackByLineResponseDto> countFeedbackByLineResponseDtoList) {
        this.sharedSourceCodeId = sharedSourceCodeId;
        this.countFeedbackByLineResponseDtoList = countFeedbackByLineResponseDtoList;
    }
}
