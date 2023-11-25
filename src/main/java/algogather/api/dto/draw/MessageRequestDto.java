package algogather.api.dto.draw;

import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequestDto {
    public enum MessageType {
        ENTER, DRAW, QUIT
    }

    @NotNull
    private MessageType messageType; // 공유소스코드 그림판 입장 여부

    @NotNull
    private Long sharedSourceCodeId;

    private String messageText;

    private PointRequestDto pointRequestDto;
}