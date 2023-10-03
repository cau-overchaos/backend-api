package algogather.api.dto.studyroom;

import algogather.api.domain.problem.ProblemProvider;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CreatedAssignmentResponseDto {
    private Long id;

    private List<ProblemInfo> problemList;

    private Long studyRoomId;

    private LocalDateTime startDate;

    private LocalDateTime duetDate;

    @Getter
    static class ProblemInfo {
        private ProblemProvider provider;

        private Long pid;
    }
}
