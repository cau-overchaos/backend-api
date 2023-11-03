package algogather.api.domain.sharedsourcecode;

import algogather.api.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class FeedbackBoard extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "feedback_board_line", nullable = false)
    private Integer line;

    @JoinColumn(name = "source_code_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private SharedSourceCode sharedSourceCode;
}
