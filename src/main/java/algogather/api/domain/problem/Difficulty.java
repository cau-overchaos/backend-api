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
public class Difficulty extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "difficulty_name")
    private String name;

    @Column(name = "difficulty_level")
    private Integer level;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty_provider")
    private ProblemProvider provider;

    @Builder
    public Difficulty(Long id, String name, Integer level, ProblemProvider provider) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.provider = provider;
    }
}
