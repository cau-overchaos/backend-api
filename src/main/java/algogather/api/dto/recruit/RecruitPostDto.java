package algogather.api.dto.recruit;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RecruitPostDto {
    private Long id;
    private String title;
    private String writerUserId;
    private String writerUserName;

    @Builder
    public RecruitPostDto(Long id, String title, String writerUserId, String writerUserName) {
        this.id = id;
        this.title = title;
        this.writerUserId = writerUserId;
        this.writerUserName = writerUserName;
    }
}
