package algogather.api.service.studyroom;

import algogather.api.domain.studyroom.StudyRoomRepository;
import algogather.api.domain.studyroom.UserStudyRoomRepository;
import algogather.api.domain.user.UserAdapter;
import algogather.api.exception.NotStudyRoomMemberException;
import algogather.api.exception.StudyRoomNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import algogather.api.domain.user.User;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudyRoomService {
    private final UserStudyRoomRepository userStudyRoomRepository;
    private final StudyRoomRepository studyRoomRepository;

    /**
     * 스터디방의 회원인지 확인한다.
     */
    public void checkStudyRoomMember(UserAdapter userAdapter, Long studyRoomId) {
        User user = userAdapter.getUser();

        findById(studyRoomId);

        userStudyRoomRepository.findByUserIdAndStudyRoomId(user.getId(), studyRoomId).orElseThrow(() -> new NotStudyRoomMemberException());
    }

    public void findById(Long studyRoomId) {
        studyRoomRepository.findById(studyRoomId).orElseThrow(() -> new StudyRoomNotFoundException());
    }
}
