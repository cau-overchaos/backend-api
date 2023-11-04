package algogather.api.service.studyroom;

import algogather.api.domain.studyroom.*;
import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.studyroom.*;
import algogather.api.exception.studyroom.*;
import algogather.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import algogather.api.domain.user.User;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static algogather.api.config.init.initConst.solvedAcUrl;

@Service
@RequiredArgsConstructor
public class StudyRoomService {
    private final UserService userService;

    private final UserStudyRoomRepository userStudyRoomRepository;
    private final StudyRoomRepository studyRoomRepository;

    /**
     * 스터디방의 회원인지 확인한다.
     */
    public void throwExceptionIfNotStudyRoomMember(UserAdapter userAdapter, Long studyRoomId) {
        User user = userAdapter.getUser();

        StudyRoom studyRoom = findById(studyRoomId);

        userStudyRoomRepository.findByUserIdAndStudyRoomId(user.getId(), studyRoom.getId()).orElseThrow(() -> new NotStudyRoomMemberException());
    }

    /**
     * 스터디방의 관리자인지 확인한다.
     */
    public void throwExceptionIfNotStudyRoomManager(UserAdapter userAdapter, Long studyRoomId) {
        User user = userAdapter.getUser();

        StudyRoom studyRoom = findById(studyRoomId);

        UserStudyRoom foundUserStudyRoom = userStudyRoomRepository.findByUserIdAndStudyRoomId(user.getId(), studyRoom.getId()).orElseThrow(() -> new NotStudyRoomMemberException());

        if(!foundUserStudyRoom.getRole().getKey().equals("MANAGER")) {
            throw new NotStudyRoomManagerException();
        }
    }

    public UserStudyRoom findUserStudyRoomByUserAdapterAndStudyRoomId(UserAdapter userAdapter, Long studyRoomId) {
        User user = userAdapter.getUser();
        StudyRoom studyRoom = findById(studyRoomId);

        return userStudyRoomRepository.findByUserIdAndStudyRoomId(user.getId(), studyRoom.getId()).orElseThrow(() -> new NotStudyRoomMemberException());
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
        UserAdapter foundUserAdaptor = userService.findByUserId(addStudyRoomMemberRequestDto.getTargetUserId());

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

    @Transactional
    public void deleteStudyMember(UserAdapter userAdapter, Long studyRoomId, DeleteStudyRoomMemberRequestDto deleteStudyRoomMemberRequestDto) {
        throwExceptionIfNotStudyRoomManager(userAdapter, studyRoomId); // 스터디룸 관리자만 스터디룸의 멤버를 삭제할 수 있다.

        StudyRoom foundStudyRoom = findById(studyRoomId);
        UserAdapter foundUserAdaptor = userService.findByUserId(deleteStudyRoomMemberRequestDto.getTargetUserId());

        UserStudyRoom userStudyRoom = findUserStudyRoomByUserAdapterAndStudyRoomId(foundUserAdaptor, foundStudyRoom.getId());

        if(userAdapter.getUser().getUserId().equals(userStudyRoom.getUser().getUserId())) { // 자기 자신을 삭제할 수 없다.
            throw new DeleteMeFromStudyRoomException();
        }

        if(userStudyRoom.getRole().equals(StudyRoomRole.MANAGER)) { // 관리자를 삭제할 수 없다.
            throw new DeleteManagerFromStudyRoomException();
        }

         userStudyRoomRepository.delete(userStudyRoom);
    }

    //TODO changeStudyRoomAuthority -> changeStudyRoomMemberAuthority로 이름 바꾸기
    @Transactional
    public boolean changeStudyRoomAuthority(UserAdapter userAdapter, Long studyRoomId, ChangeStudyRoomAuthorityRequestDto changeStudyRoomAuthorityRequestDto) {
        throwExceptionIfNotStudyRoomManager(userAdapter, studyRoomId); // 스터디룸 관리자만 스터디룸 권한을 변경할 수 있다.

        StudyRoom foundStudyRoom = findById(studyRoomId);
        UserAdapter foundUserAdaptor = userService.findByUserId(changeStudyRoomAuthorityRequestDto.getTargetUserId());

        UserStudyRoom userStudyRoom = findUserStudyRoomByUserAdapterAndStudyRoomId(foundUserAdaptor, foundStudyRoom.getId());

        if(userAdapter.getUser().getUserId().equals(userStudyRoom.getUser().getUserId())) { // 자기 자신의 권한을 변경할 수는 없다.
            throw new ChangeMyStudyRoomAuthorityException();
        }

        if(userStudyRoom.getRole() == StudyRoomRole.MANAGER) { // 스터디룸 관리자였으면 관리자에서 해제한다.
            userStudyRoom.changeStudyRoomRole(StudyRoomRole.USER);
            return false;
        }
        else{ // 일반 스터디룸 멤버였으면 관리자를 부여한다.
            userStudyRoom.changeStudyRoomRole(StudyRoomRole.MANAGER);
            return true;
        }
    }

    public StudyRoomMemberListResponseDto getStudyRoomMemberList(UserAdapter userAdapter, Long studyRoomId) throws ParseException {
        throwExceptionIfNotStudyRoomMember(userAdapter, studyRoomId); // 스터디룸 멤버만 멤버 조회가 가능하다.

        List<UserWithStudyRoomAuthorityInfoDto> userWithStudyRoomAuthorityInfoDtos = userStudyRoomRepository.findUsersWithStudyRoomInfo(studyRoomId);
        List<StudyRoomMemberInfoDto> studyRoomMemberInfoDtoList = getStudyRoomMemberInfoDtoList(userAdapter, userWithStudyRoomAuthorityInfoDtos);

        return new StudyRoomMemberListResponseDto(studyRoomMemberInfoDtoList);
    }

    private static List<StudyRoomMemberInfoDto> getStudyRoomMemberInfoDtoList(UserAdapter userAdapter, List<UserWithStudyRoomAuthorityInfoDto> userWithStudyRoomAuthorityInfoDtos) throws ParseException {
        List<StudyRoomMemberInfoDto> studyRoomMemberInfoDtoList = new ArrayList<>();

        for (UserWithStudyRoomAuthorityInfoDto userWithStudyRoomAuthorityInfoDto : userWithStudyRoomAuthorityInfoDtos) {
            URI uri = UriComponentsBuilder.fromHttpUrl(solvedAcUrl)
                    .path("/api/v3/user/show")
                    .queryParam("handle", userWithStudyRoomAuthorityInfoDto.getJudgeAccount())
                    .encode()
                    .build()
                    .toUri();
            RestTemplate restTemplate = new RestTemplate();

            RequestEntity<Void> req = RequestEntity
                    .get(uri)
                    .build();

            Long tierLevel = 0L; // solved.AC 사용자 정보를 찾으면 1이상의 값이 들어 가고, 못 찾으면 0이 들어 간다.
            String studyRoomAuthorityName = userWithStudyRoomAuthorityInfoDto.getStudyRoomRole().equals(StudyRoomRole.USER) ? "일반 멤버" : "관리자";
            boolean isValidJudgeAccount = true;
            try {
                ResponseEntity<String> response = restTemplate.exchange(req, String.class);

                JSONParser jsonParser = new JSONParser();
                JSONObject userInfo = (JSONObject) jsonParser.parse(response.getBody());

                tierLevel = (Long) userInfo.get("tier");
            }
            catch (HttpClientErrorException e) { // 백준 계정이 잘못 되어서 404 NOT_FOUND 등이 반환되었을 때 예외처리
                isValidJudgeAccount = false;
            }

            studyRoomMemberInfoDtoList.add(StudyRoomMemberInfoDto.builder()
                    .userId(userWithStudyRoomAuthorityInfoDto.getUserId())
                    .name(userWithStudyRoomAuthorityInfoDto.getName())
                    .isValidJudgeAccount(isValidJudgeAccount)
                    .judgeAccount(userWithStudyRoomAuthorityInfoDto.getJudgeAccount())
                    .tierLevel(tierLevel)
                    .studyRoomRole(userWithStudyRoomAuthorityInfoDto.getStudyRoomRole())
                    .studyRoomRoleName(studyRoomAuthorityName)
                    .isMe(userAdapter.getUser().getId().equals(userWithStudyRoomAuthorityInfoDto.getId())) // 현재 인원 페이지를 보고 있는, 로그인한 사용자이면 true, 아니면 false를 대입한다.
                    .build());
        }
        return studyRoomMemberInfoDtoList;
    }
}
