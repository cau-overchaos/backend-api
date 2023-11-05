package algogather.api.domain.sharedsourcecode;

import algogather.api.domain.BaseTimeEntity;
import algogather.api.domain.user.User;
import lombok.AccessLevel;
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

    @Column(name = "feedback_text")
    private String text;

    @Column(name = "feedback_source_code_line_number")
    private Long sourceCodeLineNumber;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "shared_source_code_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private SharedSourceCode sharedSourceCode;

    @JoinColumn(name = "reply_parent_feedback_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Feedback replyParentFeedback;
}
