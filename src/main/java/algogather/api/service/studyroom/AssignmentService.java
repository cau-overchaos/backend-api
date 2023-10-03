package algogather.api.service.studyroom;

import algogather.api.domain.assignment.AssignmentProblemRepository;
import algogather.api.dto.studyroom.AssignmentCreateForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentProblemRepository assignmentProblemRepository;
    //TODO CreatedAssignmentResponseDto 만들기
    public Object createAssignment(AssignmentCreateForm assignmentCreateForm) {

        // 문제목록들 문제가 존재하는지 검증

    }
}
