package algogather.api.dto.studyroom;

import algogather.api.dto.problem.ProblemInfoRequestDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class AssignmentCreateForm {
    @Schema(description = "문제 리스트")
    @NotEmpty(message = "문제 목록이 비어있습니다.")
    @Size(max = 30, message = "문제는 한번에 30개까지 등록이 가능합니다")
    private List<ProblemInfoRequestDto> problemList;

    @Schema(description = "과제 시작 날짜 yyyy-MM-dd HH:mm 형식")
    @NotNull(message = "과제 시작 날짜를 입력해주세요.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startDate;

    @Schema(description = "과제 종료 날짜 yyyy-MM-dd HH:mm 형식")
    @NotNull(message = "과제 종료 날짜를 입력해주세요.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime dueDate;

    @Builder
    public AssignmentCreateForm(List<ProblemInfoRequestDto> problemList, LocalDateTime startDate, LocalDateTime dueDate) {
        this.problemList = problemList;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }
}
