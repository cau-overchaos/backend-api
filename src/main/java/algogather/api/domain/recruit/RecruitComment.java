package algogather.api.domain.recruit;

import algogather.api.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import algogather.api.domain.user.User;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class RecruitComment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_text", length = 150)
    private String text;

    @JoinColumn(name = "recruit_post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private RecruitPost recruitPost;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public RecruitComment(Long id, String text, RecruitPost recruitPost, User user) {
        this.id = id;
        this.text = text;
        this.recruitPost = recruitPost;
        this.user = user;
    }
}


