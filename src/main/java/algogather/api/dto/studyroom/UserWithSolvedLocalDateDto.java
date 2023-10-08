package algogather.api.dto.studyroom;

import algogather.api.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserWithSolvedLocalDateDto {
    private User user;
    private LocalDateTime solvedDate;
}
