package algogather.api.dto.recruit;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CreateRecruitPostRequestForm {
    @NotNull
    private Long studyRoomId;

    @NotEmpty
    private String title;

    @NotEmpty
    private String text;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate dueDate;
}
