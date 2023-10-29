package algogather.api.controller.studyroom;

import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.api.ApiResponse;
import algogather.api.dto.studyroom.*;
import algogather.api.service.studyroom.AssignmentService;
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

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/studyrooms")
@Tag(name = "스터디방", description = "스터디방 관련 API 입니다.")
public class StudyRoomController {

    private final StudyRoomService studyRoomService;
    private final AssignmentService assignmentService;

    @Operation(summary = "모든 스터디방 목록", description = "모든 스터디방 목록 불러오기 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping
    public ResponseEntity<ApiResponse<?>> findAllStudyRoom() {

        StudyRoomResponseDto allStudyRoomList = studyRoomService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucessWithDataAndMessage(allStudyRoomList, "스터디방 목록을 성공적으로 불러왔습니다."));
    }

    @Operation(summary = "내 스터디방 목록", description = "내 스터디방 목록 불러오기 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping("/participated")
    public ResponseEntity<ApiResponse<?>> findAllStudyRoom(@AuthenticationPrincipal UserAdapter userAdapter) {

        StudyRoomResponseDto myStudyRoomList = studyRoomService.findMyStudyRooms(userAdapter);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucessWithDataAndMessage(myStudyRoomList, "내 스터디방 목록을 성공적으로 불러왔습니다."));
    }


    @Operation(summary = "스터디방 생성", description = "스터디방 생성 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping
    public ResponseEntity<ApiResponse<?>> createStudyRoom(@AuthenticationPrincipal UserAdapter userAdapter, @Valid @RequestBody StudyRoomCreateForm studyRoomCreateForm) {

        CreatedStudyRoomResponseDto createdStudyRoom = studyRoomService.createStudyRoom(userAdapter, studyRoomCreateForm);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.sucessWithDataAndMessage(createdStudyRoom, "스터디방이 성공적으로 생성되었습니다."));

    }

    @Operation(summary = "과제 생성", description = "과제 생성 API입니다.")
    @PostMapping("/{studyRoomId}/assignments")
    public ResponseEntity<ApiResponse<?>> createAssignment(@AuthenticationPrincipal UserAdapter userAdapter, @PathVariable Long studyRoomId, @Valid @RequestBody AssignmentCreateForm assignmentCreateForm) {

        CreatedAssignmentResponseDto createdAssignmentResponseDto = assignmentService.createAssignment(userAdapter, studyRoomId, assignmentCreateForm);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.sucessWithDataAndMessage(createdAssignmentResponseDto, "과제가 성공적으로 생성되었습니다."));
    }

    @Operation(summary = "과제 목록", description = "과제 목록 API입니다.")
    @GetMapping("/{studyRoomId}/assignments")
    public ResponseEntity<ApiResponse<?>> getAssignments(@AuthenticationPrincipal UserAdapter userAdapter, @PathVariable Long studyRoomId) {
        AssignmentResponseDto assignmentList = assignmentService.getAssignmentList(userAdapter, studyRoomId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucessWithDataAndMessage(assignmentList, "과제를 성공적으로 불러왔습니다."));
    }

    @Operation(summary = "멤버 추가", description = "멤버 추가 API입니다.")
    @PostMapping("/{studyRoomId}/members")
    public ResponseEntity<ApiResponse<?>> addStudyMember(@AuthenticationPrincipal UserAdapter userAdapter, @PathVariable Long studyRoomId, @RequestBody AddStudyRoomMemberRequestDto addStudyRoomMemberRequestDto) {
        AddStudyRoomMemberResponseDto addStudyRoomMemberResponseDto = studyRoomService.addStudyMember(userAdapter, studyRoomId, addStudyRoomMemberRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucessWithDataAndMessage(addStudyRoomMemberResponseDto, "멤버를 성공적으로 추가했습니다."));
    }
}
