package algogather.api.domain.sharedsourcecode;

import algogather.api.domain.BaseTimeEntity;
import algogather.api.domain.problem.Problem;
import algogather.api.domain.programminglanguage.ProgrammingLanguage;
import algogather.api.domain.studyroom.StudyRoom;
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
public class SharedSourceCode extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shared_source_code_text")
    private String text;

    @Column(name = "shared_source_code_title")
    private String title;

    @JoinColumn(name = "problem_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Problem problem;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "study_room_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private StudyRoom studyRoom;

    @JoinColumn(name = "programming_language_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProgrammingLanguage programmingLanguage;
}
