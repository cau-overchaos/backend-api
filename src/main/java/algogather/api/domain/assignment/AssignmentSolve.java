package algogather.api.domain.assignment;

import algogather.api.domain.BaseTimeEntity;
import algogather.api.domain.studyroom.StudyRoom;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import algogather.api.domain.user.User;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class AssignmentSolve extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "assignment_problem_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AssignmentProblem assignmentProblem;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
