package algogather.api.service.sharedsourcecode;

import algogather.api.domain.problem.Problem;
import algogather.api.domain.programminglanguage.ProgrammingLanguage;
import algogather.api.domain.sharedsourcecode.SharedSourceCode;
import algogather.api.domain.sharedsourcecode.SharedSourceCodeRepository;
import algogather.api.domain.studyroom.StudyRoom;
import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.sharedsourcecode.CreateSharedSourceCodeRequestForm;
import algogather.api.dto.sharedsourcecode.SharedSourceCodeListResponseDto;
import algogather.api.dto.sharedsourcecode.SharedSourceCodeResponseDto;
import algogather.api.service.programmingllanguage.ProgrammingLanguageService;
import algogather.api.service.studyroom.ProblemService;
import algogather.api.service.studyroom.StudyRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SharedSourceCodeService {
    private final StudyRoomService studyRoomService;
    private final ProblemService problemService;
    private final ProgrammingLanguageService programmingLanguageService;
    private final SharedSourceCodeRepository sharedSourceCodeRepository;

    public SharedSourceCodeResponseDto saveSharedSourceCode(Long studyRoomId, CreateSharedSourceCodeRequestForm createSharedSourceCodeRequestForm, UserAdapter userAdapter) {
        studyRoomService.throwExceptionIfNotStudyRoomMember(userAdapter, studyRoomId); // 스터디룸 멤버만 공유 소스코드를 작성할 수 있다.

        StudyRoom studyRoom = studyRoomService.findById(studyRoomId);
        Problem problem = problemService.findByPidAndProvider(createSharedSourceCodeRequestForm.getProblemInfoRequestDto().getPid(), createSharedSourceCodeRequestForm.getProblemInfoRequestDto().getProvider());
        ProgrammingLanguage programmingLanguage = programmingLanguageService.findById(createSharedSourceCodeRequestForm.getProgrammingLanguageId());

        SharedSourceCode newSharedSourceCode = SharedSourceCode.builder()
                .title(createSharedSourceCodeRequestForm.getTitle())
                .sourceCodeText(createSharedSourceCodeRequestForm.getSourceCode())
                .user(userAdapter.getUser())
                .studyRoom(studyRoom)
                .programmingLanguage(programmingLanguage)
                .problem(problem)
                .build();

        SharedSourceCode savedSharedSourceCode = sharedSourceCodeRepository.save(newSharedSourceCode);

        return SharedSourceCodeResponseDto.builder()
                .id(savedSharedSourceCode.getId())
                .sharedSourceCodeTitle(savedSharedSourceCode.getTitle())
                .problemDifficultyLevel(savedSharedSourceCode.getProblem().getDifficulty().getLevel())
                .problemTitle(savedSharedSourceCode.getProblem().getTitle())
                .writerName(savedSharedSourceCode.getUser().getName())
                .sourceCodeText(savedSharedSourceCode.getSourceCodeText())
                .programmingLanguage(savedSharedSourceCode.getProgrammingLanguage().getName())
                .createdAt(savedSharedSourceCode.getCreatedAt())
                .build();
    }

    public SharedSourceCodeListResponseDto findAllSharedSourceCodeByStudyRoomId(Long studyRoomId, UserAdapter userAdapter) {
        studyRoomService.throwExceptionIfNotStudyRoomMember(userAdapter, studyRoomId); // 스터디룸 멤버만 공유 소스코드 목록을 볼 수 있다.

        List<SharedSourceCode> sharedSourceCodeList = sharedSourceCodeRepository.findByStudyRoomIdOrderByCreatedAt(studyRoomId);

        return new SharedSourceCodeListResponseDto(sharedSourceCodeList);
    }
}
