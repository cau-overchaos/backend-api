package algogather.api.domain.assignment;

import algogather.api.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import algogather.api.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(
        uniqueConstraints = {
                @UniqueConstraint(  // 특정 과제에 대해서 특정 유저가 문제를 풀었는지 안풀었는지 여부는 한 번만 저장되므로 unique하다
                        columnNames = {"assignment_problem_id", "user_id"}
                )
        }
)
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

    @Column(name = "solved_date")
    private LocalDateTime solvedDate;

    @Builder
    public AssignmentSolve(Long id, AssignmentProblem assignmentProblem, User user, LocalDateTime solvedDate) {
        this.id = id;
        this.assignmentProblem = assignmentProblem;
        this.user = user;
        this.solvedDate = solvedDate;
    }
}
