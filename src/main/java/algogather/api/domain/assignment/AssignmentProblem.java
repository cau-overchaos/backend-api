package algogather.api.domain.assignment;

import algogather.api.domain.BaseTimeEntity;
import algogather.api.domain.problem.Problem;
import algogather.api.domain.studyroom.StudyRoom;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class AssignmentProblem extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "assignment_problem_start_date")
    private LocalDateTime startDate;

    @Column(name = "assignment_problem_due_date")
    private LocalDateTime dueDate;

    @JoinColumn(name = "study_room_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private StudyRoom studyRoom;

    @JoinColumn(name = "problem_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Problem problem;

    @Builder
    public AssignmentProblem(Long id, LocalDateTime startDate, LocalDateTime dueDate, StudyRoom studyRoom, Problem problem) {
        this.id = id;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.studyRoom = studyRoom;
        this.problem = problem;
    }
}
