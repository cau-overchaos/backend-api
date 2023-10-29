package algogather.api.service.studyroom;

import algogather.api.domain.studyroom.*;
import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.studyroom.*;
import algogather.api.exception.studyroom.AlreadyExistingStudyRoomMemberException;
import algogather.api.exception.studyroom.NotStudyRoomManagerException;
import algogather.api.exception.studyroom.NotStudyRoomMemberException;
import algogather.api.exception.studyroom.StudyRoomNotFoundException;
import algogather.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import algogather.api.domain.user.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudyRoomService {
    private final UserService userService;

    private final UserStudyRoomRepository userStudyRoomRepository;
    private final StudyRoomRepository studyRoomRepository;

    /**
     * 스터디방의 회원인지 확인한다.
     */
    public void throwExceptionIfNotStudyRoomMember(UserAdapter userAdapter, Long studyRoomId) {
        User user = userAdapter.getUser();

        findById(studyRoomId);

        userStudyRoomRepository.findByUserIdAndStudyRoomId(user.getId(), studyRoomId).orElseThrow(() -> new NotStudyRoomMemberException());
    }

    /**
     * 스터디방의 관리자인지 확인한다.
     */
    public void throwExceptionIfNotStudyRoomManager(UserAdapter userAdapter, Long studyRoomId) {
        User user = userAdapter.getUser();

        findById(studyRoomId);

        UserStudyRoom foundUserStudyRoom = userStudyRoomRepository.findByUserIdAndStudyRoomId(user.getId(), studyRoomId).orElseThrow(() -> new NotStudyRoomMemberException());

        if(!foundUserStudyRoom.getRole().getKey().equals("MANAGER")) {
            throw new NotStudyRoomManagerException();
        }

    }

    @Transactional
    public CreatedStudyRoomResponseDto createStudyRoom(UserAdapter userAdapter, StudyRoomCreateForm studyRoomCreateForm) {
        StudyRoom newStudyRoom = StudyRoom.builder()
                .title(studyRoomCreateForm.getTitle())
                .description(studyRoomCreateForm.getDescription())
                .studyRoomVisibility(studyRoomCreateForm.getStudyRoomVisibility())
                .maxUserCnt(studyRoomCreateForm.getMaxUserCnt())
                .build();

        UserStudyRoom newUserStudyRoom = UserStudyRoom.builder().studyRoom(newStudyRoom)
                .user(userAdapter.getUser())
                .role(StudyRoomRole.MANAGER) // 스터디방을 생성한 사람은 관리자가 된다.
                .build();

        StudyRoom createdStudyRoom = studyRoomRepository.save(newStudyRoom);
        UserStudyRoom createdUserStudyRoom = userStudyRoomRepository.save(newUserStudyRoom);

        return new CreatedStudyRoomResponseDto(createdStudyRoom, createdUserStudyRoom.getUser().getUserId());
    }

    public StudyRoom findById(Long studyRoomId) {
        return studyRoomRepository.findById(studyRoomId).orElseThrow(() -> new StudyRoomNotFoundException());
    }

    public StudyRoomResponseDto findAll() {
        List<StudyRoom> allStudyRooms = studyRoomRepository.findAll();
        return new StudyRoomResponseDto(allStudyRooms);
    }

    public StudyRoomResponseDto findMyStudyRooms(UserAdapter userAdapter) {
        List<StudyRoom> myStudyRooms = studyRoomRepository.findStudyRoomsByUserId(userAdapter.getUser().getId());
        return new StudyRoomResponseDto(myStudyRooms);
    }

    @Transactional
    public AddStudyRoomMemberResponseDto addStudyMember(UserAdapter userAdapter, Long studyRoomId, AddStudyRoomMemberRequestDto addStudyRoomMemberRequestDto) {
        throwExceptionIfNotStudyRoomManager(userAdapter, studyRoomId); // 스터디룸 관리자만 스터디룸에 사람을 초대할 수 있다.

        StudyRoom foundStudyRoom = findById(studyRoomId);
        UserAdapter foundUserAdaptor = userService.findByUserId(addStudyRoomMemberRequestDto.getInvitedUserId());


        // 해당 유저가 스터디방에 이미 존재하는지 검증
        Optional<UserStudyRoom> existingUserStudyRoom = userStudyRoomRepository.findByUserIdAndStudyRoomId(foundUserAdaptor.getUser().getId(), foundStudyRoom.getId());
        if(existingUserStudyRoom.isPresent()) {
            throw new AlreadyExistingStudyRoomMemberException();
        }

        UserStudyRoom newUserStudyRoom = UserStudyRoom.builder()
                .studyRoom(foundStudyRoom)
                .user(foundUserAdaptor.getUser())
                .role(StudyRoomRole.USER)
                .build();

        UserStudyRoom savedUserStudyRoom = userStudyRoomRepository.save(newUserStudyRoom);

        return new AddStudyRoomMemberResponseDto(savedUserStudyRoom.getStudyRoom().getId(), savedUserStudyRoom.getUser().getUserId());
    }
}
