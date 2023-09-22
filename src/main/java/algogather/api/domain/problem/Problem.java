package algogather.api.domain.problem;

import algogather.api.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
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
    private Integer pid;

    @Enumerated(EnumType.STRING)
    @Column(name = "problem_provider")
    private ProblemProvider provider;

    @JoinColumn(name = "difficulty_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Difficulty difficulty;
}
