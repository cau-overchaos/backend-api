package algogather.api.dto.draw;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PointRequestDto {
    public enum IsDelete {
        YES, NO
    }

    @NotNull
    private IsDelete isDelete;
    @NotNull
    private Long x;
    @NotNull
    private Long y;
    @NotNull
    private String color;
}
