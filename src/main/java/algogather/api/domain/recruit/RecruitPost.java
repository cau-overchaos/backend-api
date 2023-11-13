package algogather.api.domain.recruit;

import algogather.api.domain.BaseTimeEntity;
import algogather.api.domain.studyroom.StudyRoom;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import algogather.api.domain.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class RecruitPost extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recruit_post_title")
    private String title;

    @Column(name = "recruit_post_text")
    private String text;

    @Column(name = "recruit_post_due_date")
    private LocalDate dueDate;

    @JoinColumn(name = "study_room_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private StudyRoom studyRoom;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public RecruitPost(Long id, String title, String text, LocalDate dueDate, StudyRoom studyRoom, User user) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.dueDate = dueDate;
        this.studyRoom = studyRoom;
        this.user = user;
    }
}
