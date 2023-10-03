package algogather.api.dto.studyroom;

import algogather.api.domain.problem.ProblemProvider;
import algogather.api.dto.problem.ProblemInfoDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class AssignmentCreateForm {
    @Schema(description = "문제 리스트")
    @NotEmpty(message = "문제 목록이 비어있습니다.")
    @Size(max = 30, message = "문제는 한번에 30개까지 등록이 가능합니다")
    private List<ProblemInfoDto> problemList;

    @Schema(description = "스터디방 번호")
    @NotEmpty(message = "스터디방 번호가 없습니다.")
    private Long studyRoomId;

    @Schema(description = "과제 시작 날짜 yyyy-MM-dd HH:mm 형식")
    @NotEmpty(message = "과제 시작 날짜를 입력해주세요.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startDate;

    @Schema(description = "과제 종료 날짜 yyyy-MM-dd HH:mm 형식")
    @NotEmpty(message = "과제 종료 날짜를 입력해주세요.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime duetDate;
}
