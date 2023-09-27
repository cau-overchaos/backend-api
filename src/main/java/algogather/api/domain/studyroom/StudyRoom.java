package algogather.api.domain.studyroom;

import algogather.api.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class StudyRoom extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "study_room_title")
    private String title;

    @Column(name = "study_room_description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "study_room_visibility")
    private StudyRoomVisibility studyRoomVisibility;

    @Column(name = "study_roome_max_user_cnt")
    private Integer maxUserCnt;

    @Builder
    public StudyRoom(Long id, String title, String description, StudyRoomVisibility studyRoomVisibility, Integer maxUserCnt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.studyRoomVisibility = studyRoomVisibility;
        this.maxUserCnt = maxUserCnt;
    }
}
