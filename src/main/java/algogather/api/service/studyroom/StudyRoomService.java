package algogather.api.service.studyroom;

import algogather.api.domain.studyroom.*;
import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.studyroom.CreatedStudyRoomResponseDto;
import algogather.api.dto.studyroom.StudyRoomCreateForm;
import algogather.api.dto.studyroom.StudyRoomResponseDto;
import algogather.api.exception.NotStudyRoomMemberException;
import algogather.api.exception.StudyRoomNotFoundException;
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

        return new CreatedStudyRoomResponseDto(createdStudyRoom, createdUserStudyRoom.getUser().getName());
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
}
