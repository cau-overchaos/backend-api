package algogather.api.dto.recruit;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CreateRecruitPostRequestForm {
    @NotNull
    private Long studyRoomId;

    @NotEmpty
    @Size(max = 50, message = "제목은 50자 이하여야합니다.")

    private String title;

    @NotEmpty
    @Size(max = 500, message = "내용은 500자 이하여야합니다.")
    private String text;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate dueDate;
}
