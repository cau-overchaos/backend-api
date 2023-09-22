package algogather.api.domain.feedback;

import algogather.api.domain.BaseTimeEntity;
import algogather.api.domain.problem.Problem;
import algogather.api.domain.programminglanguage.ProgrammingLanguage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class SourceCode extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_code_text")
    private String text;

    @Column(name = "source_code_title")
    private String title;

    @JoinColumn(name = "problem_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Problem problem;

    @JoinColumn(name = "programming_language_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProgrammingLanguage programmingLanguage;
}
