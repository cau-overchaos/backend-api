package algogather.api.dto.draw;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PointRequestDto {
    @NotNull
    private boolean isDeleteAll;
    @NotNull
    private boolean isDelete;
    @NotNull
    private Long startX;
    @NotNull
    private Long startY;
    @NotNull
    private Long endX;
    @NotNull
    private Long endY;
    @NotNull
    private String color;
}
