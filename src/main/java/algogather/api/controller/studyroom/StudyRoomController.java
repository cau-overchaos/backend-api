package algogather.api.controller.studyroom;

import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.api.ApiResponse;
import algogather.api.dto.studyroom.StudyRoomCreateForm;
import algogather.api.service.studyroom.StudyRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/studyrooms")
@Tag(name = "스터디방", description = "스터디방 관련 API 입니다.")
public class StudyRoomController {

    private final StudyRoomService studyRoomService;
//    @Operation(summary = "스터디룸 테스트중!!", description = "스터디룸 테스트중!!")
//    @GetMapping
//    public ResponseEntity<ApiResponse<?>> test (@AuthenticationPrincipal UserAdapter userAdapter, Long studyRoomId) {
//        studyRoomService.checkStudyRoomMember(userAdapter, studyRoomId); // 사용자가 스터디룸의 멤버인지 검증한다.
//
//        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucess("test 성공!"));
//    }

    @Operation(summary = "스터디방 생성", description = "스터디방 생성 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping
    public ResponseEntity<ApiResponse<?>> createStudyRoom(@AuthenticationPrincipal UserAdapter userAdapter, @RequestBody StudyRoomCreateForm studyRoomCreateForm) {

        studyRoomService.createStudyRoom(userAdapter, studyRoomCreateForm);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucess("스터디방이 성공적으로 생성되었습니다."));
    }

}
