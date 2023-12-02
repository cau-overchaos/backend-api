package algogather.api.dto.notification;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class NotificationReadRequestDto {

    @NotNull
    private Long notificationId;

    @Builder
    public NotificationReadRequestDto(Long notificationId) {
        this.notificationId = notificationId;
    }
}
