package algogather.api.domain.problem;

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
public class Problem extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "problem_id")
    private Long pid;

    @Column(name = "problem_title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "problem_provider")
    private ProblemProvider provider;

    @JoinColumn(name = "difficulty_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Difficulty difficulty;

    @Builder
    public Problem(Long id, Long pid, String title, ProblemProvider provider, Difficulty difficulty) {
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.provider = provider;
        this.difficulty = difficulty;
    }
}
