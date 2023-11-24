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
import algogather.api.exception.sharedsourcecode.SharedSourceCodeAndStudyRoomNotMatchingException;
import algogather.api.exception.sharedsourcecode.SharedSourceCodeNotFoundException;
import algogather.api.service.programmingllanguage.ProgrammingLanguageService;
import algogather.api.service.studyroom.ProblemService;
import algogather.api.service.studyroom.StudyRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SharedSourceCodeService {
    private final StudyRoomService studyRoomService;
    private final ProblemService problemService;
    private final ProgrammingLanguageService programmingLanguageService;
    private final SharedSourceCodeRepository sharedSourceCodeRepository;

    @Transactional
    public SharedSourceCodeResponseDto save(Long studyRoomId, CreateSharedSourceCodeRequestForm createSharedSourceCodeRequestForm, UserAdapter userAdapter) {
        studyRoomService.throwExceptionIfNotStudyRoomMember(userAdapter, studyRoomId); // 스터디룸 멤버만 공유 소스코드를 작성할 수 있다.

        StudyRoom studyRoom = studyRoomService.findById(studyRoomId);
        Problem problem = problemService.findByPidAndProvider(createSharedSourceCodeRequestForm.getProblemInfoRequestDto().getPid(), createSharedSourceCodeRequestForm.getProblemInfoRequestDto().getProvider());
        ProgrammingLanguage programmingLanguage = programmingLanguageService.findById(createSharedSourceCodeRequestForm.getProgrammingLanguageId());

        programmingLanguageService.throwExceptionIfStudyRoomIdAndProgrammingLanguageIdNotMatching(studyRoom.getId(), programmingLanguage.getId()); // 스터디방 사용 언어로만 코드를 작성할 수 있다.

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
                .sharedSourceCodeId(savedSharedSourceCode.getId())
                .sharedSourceCodeTitle(savedSharedSourceCode.getTitle())
                .problemDifficultyLevel(savedSharedSourceCode.getProblem().getDifficulty().getLevel())
                .problemTitle(savedSharedSourceCode.getProblem().getTitle())
                .writerUserId(savedSharedSourceCode.getUser().getUserId())
                .writerName(savedSharedSourceCode.getUser().getName())
                .sourceCodeText(savedSharedSourceCode.getSourceCodeText())
                .programmingLanguageId(savedSharedSourceCode.getProgrammingLanguage().getId())
                .programmingLanguageName(savedSharedSourceCode.getProgrammingLanguage().getName())
                .createdAt(savedSharedSourceCode.getCreatedAt())
                .build();
    }

    public SharedSourceCodeListResponseDto findAllByStudyRoomId(Long studyRoomId, UserAdapter userAdapter) {
        studyRoomService.throwExceptionIfNotStudyRoomMember(userAdapter, studyRoomId); // 스터디룸 멤버만 공유 소스코드 목록을 볼 수 있다.

        StudyRoom studyRoom = studyRoomService.findById(studyRoomId);

        List<SharedSourceCode> sharedSourceCodeList = sharedSourceCodeRepository.findByStudyRoomIdOrderByIdDesc(studyRoom.getId());

        return new SharedSourceCodeListResponseDto(sharedSourceCodeList);
    }

    public SharedSourceCodeResponseDto findByIdAndReturnResponseDto(Long studyRoomId, Long sourceCodeId, UserAdapter userAdapter) {
        studyRoomService.throwExceptionIfNotStudyRoomMember(userAdapter, studyRoomId); // 스터디룸 멤버만 특정 공유 소스코드를 조회할 수 있다.

        StudyRoom studyRoom = studyRoomService.findById(studyRoomId);

        SharedSourceCode sharedSourceCode = sharedSourceCodeRepository.findById(sourceCodeId).orElseThrow(SharedSourceCodeNotFoundException::new);

        validateSharedSourceMatchingWithStudyRoom(sharedSourceCode, studyRoom); // 해당 스터디방의 소스코드인지 검증

        return SharedSourceCodeResponseDto.builder()
                .sharedSourceCodeId(sharedSourceCode.getId())
                .sharedSourceCodeTitle(sharedSourceCode.getTitle())
                .problemDifficultyLevel(sharedSourceCode.getProblem().getDifficulty().getLevel())
                .problemTitle(sharedSourceCode.getProblem().getTitle())
                .writerUserId(sharedSourceCode.getUser().getUserId())
                .writerName(sharedSourceCode.getUser().getName())
                .sourceCodeText(sharedSourceCode.getSourceCodeText())
                .programmingLanguageId(sharedSourceCode.getProgrammingLanguage().getId())
                .programmingLanguageName(sharedSourceCode.getProgrammingLanguage().getName())
                .createdAt(sharedSourceCode.getCreatedAt())
                .build();
    }

    public SharedSourceCode findById(Long studyRoomId, Long sourceCodeId, UserAdapter userAdapter) {
        studyRoomService.throwExceptionIfNotStudyRoomMember(userAdapter, studyRoomId); // 스터디룸 멤버만 특정 공유 소스코드를 조회할 수 있다.

        StudyRoom studyRoom = studyRoomService.findById(studyRoomId);

        SharedSourceCode sharedSourceCode = sharedSourceCodeRepository.findById(sourceCodeId).orElseThrow(SharedSourceCodeNotFoundException::new);

        validateSharedSourceMatchingWithStudyRoom(sharedSourceCode, studyRoom); // 해당 스터디방의 소스코드인지 검증

        return sharedSourceCode;
    }

    public SharedSourceCode findById(Long sourceCodeId) {

        return sharedSourceCodeRepository.findById(sourceCodeId).orElseThrow(SharedSourceCodeNotFoundException::new);
    }

    private void validateSharedSourceMatchingWithStudyRoom(SharedSourceCode sharedSourceCode, StudyRoom studyRoom) {
        sharedSourceCodeRepository.findByIdAndStudyRoomId(sharedSourceCode.getId(), studyRoom.getId()).orElseThrow(SharedSourceCodeAndStudyRoomNotMatchingException::new);
    }
}
