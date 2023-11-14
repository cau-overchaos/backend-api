package algogather.api.domain.sharedsourcecode;

import algogather.api.domain.BaseTimeEntity;
import algogather.api.domain.user.User;
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
public class Feedback extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "feedback_comment", length = 150)
    private String comment;

    @Column(name = "feedback_source_code_line_number")
    private Long sourceCodeLineNumber;

    @Column(name = "feedback_is_deleted")
    private Boolean isDeleted;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "shared_source_code_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private SharedSourceCode sharedSourceCode;

    @JoinColumn(name = "reply_parent_feedback_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Feedback replyParentFeedback;

    public void changeComment(String comment) {
        this.comment = comment;
    }

    public void changeIsDeletedToTrue() {
        this.isDeleted = true;
    }

    @Builder
    public Feedback(Long id, String comment, Long sourceCodeLineNumber, Boolean isDeleted, User user, SharedSourceCode sharedSourceCode, Feedback replyParentFeedback) {
        this.id = id;
        this.comment = comment;
        this.sourceCodeLineNumber = sourceCodeLineNumber;
        this.isDeleted = isDeleted;
        this.user = user;
        this.sharedSourceCode = sharedSourceCode;
        this.replyParentFeedback = replyParentFeedback;
    }
}
