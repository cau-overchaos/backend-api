package algogather.api.controller.studyroom;


import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.api.ApiResponse;
import algogather.api.dto.programminglanguage.ProgrammingLanguageListResponseDto;
import algogather.api.dto.studyroom.*;
import algogather.api.service.studyroom.AssignmentService;
import algogather.api.service.studyroom.CompileService;
import algogather.api.service.studyroom.StudyRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
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
    private final CompileService compileService;

    @Operation(summary = "모든 스터디방 목록", description = "모든 스터디방 목록 불러오기 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping
    public ResponseEntity<ApiResponse<StudyRoomListResponseDto>> findAllStudyRoom() {

        StudyRoomListResponseDto allStudyRoomList = studyRoomService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucessWithDataAndMessage(allStudyRoomList, "스터디방 목록을 성공적으로 불러왔습니다."));
    }

    @Operation(summary = "자신이 관리자인 스터디방 목록 조회", description = "자신이 관리자인 스터디방 목록 조회 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @GetMapping("/i-am-manager")
    public ResponseEntity<ApiResponse<StudyRoomListResponseDto>> getStudyRoomListWhichIsCurrentUserIsManager(@Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {
        StudyRoomListResponseDto studyRoomListWhichIsCurrentUserIsManager = studyRoomService.getStudyRoomListWhichIsCurrentUserIsManager(userAdapter);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucessWithDataAndMessage(studyRoomListWhichIsCurrentUserIsManager, "현재 유저가 관리자인 스터디방 목록을 성공적으로 불러왔습니다."));

    }

    @Operation(summary = "내 스터디방 목록", description = "내 스터디방 목록 불러오기 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping("/participated")
    public ResponseEntity<ApiResponse<StudyRoomListResponseDto>> findAllStudyRoom(@Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {

        StudyRoomListResponseDto myStudyRoomList = studyRoomService.findMyStudyRooms(userAdapter);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucessWithDataAndMessage(myStudyRoomList, "내 스터디방 목록을 성공적으로 불러왔습니다."));
    }


    @Operation(summary = "스터디방 생성", description = "스터디방 생성 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping
    public ResponseEntity<ApiResponse<CreatedStudyRoomResponseDto>> createStudyRoom(@Valid @RequestBody StudyRoomCreateForm studyRoomCreateForm, @Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {

        CreatedStudyRoomResponseDto createdStudyRoom = studyRoomService.createStudyRoom(userAdapter, studyRoomCreateForm);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.sucessWithDataAndMessage(createdStudyRoom, "스터디방이 성공적으로 생성되었습니다."));

    }

    @Operation(summary = "스터디방 정보 조회", description = "스터디방 정보 조회 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping("/{studyRoomId}")
    public ResponseEntity<ApiResponse<StudyRoomInfoResponseDto>> createStudyRoom(@PathVariable Long studyRoomId, @Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {

        StudyRoomInfoResponseDto studyRoomInfo = studyRoomService.getStudyRoomInfo(studyRoomId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.sucessWithDataAndMessage(studyRoomInfo, "스터디방 정보를 성공적으로 불러왔습니다."));

    }

    @Operation(summary = "과제 생성", description = "과제 생성 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "접근 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping("/{studyRoomId}/assignments")
    public ResponseEntity<ApiResponse<CreatedAssignmentListResponseDto>> createAssignment(@PathVariable Long studyRoomId, @Valid @RequestBody AssignmentCreateForm assignmentCreateForm, @Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {

        CreatedAssignmentListResponseDto createdAssignmentListResponseDto = assignmentService.createAssignment(userAdapter, studyRoomId, assignmentCreateForm);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.sucessWithDataAndMessage(createdAssignmentListResponseDto, "과제가 성공적으로 생성되었습니다."));
    }

    @Operation(summary = "과제 목록", description = "과제 목록 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "접근 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping("/{studyRoomId}/assignments")
    public ResponseEntity<ApiResponse<AssignmentResponseDto>> getAssignments(@PathVariable Long studyRoomId, @Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {
        AssignmentResponseDto assignmentList = assignmentService.getAssignmentList(userAdapter, studyRoomId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucessWithDataAndMessage(assignmentList, "과제를 성공적으로 불러왔습니다."));
    }

    @Operation(summary = "멤버 추가", description = "멤버 추가 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "접근 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping("/{studyRoomId}/members/add")
    public ResponseEntity<ApiResponse<AddStudyRoomMemberResponseDto>> addStudyMember(@PathVariable Long studyRoomId, @Valid @RequestBody AddStudyRoomMemberRequestDto addStudyRoomMemberRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {
        AddStudyRoomMemberResponseDto addStudyRoomMemberResponseDto = studyRoomService.addStudyMember(userAdapter, studyRoomId, addStudyRoomMemberRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.sucessWithDataAndMessage(addStudyRoomMemberResponseDto, "멤버를 성공적으로 추가했습니다."));
    }


    @Operation(summary = "스터디방 멤버 삭제", description = "스터디방 멤버 삭제 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "접근 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping("/{studyRoomId}/members/delete")
    public ResponseEntity<ApiResponse<?>> deleteStudyMember(@PathVariable Long studyRoomId, @Valid @RequestBody DeleteStudyRoomMemberRequestDto deleteStudyRoomMemberRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {
        studyRoomService.deleteStudyMember(userAdapter, studyRoomId, deleteStudyRoomMemberRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucess("멤버를 성공적으로 삭제했습니다."));
    }

    @Operation(summary = "멤버 관리자로 설정", description = "멤버 관리자 설정 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "접근 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping("/{studyRoomId}/members/authority")
    public ResponseEntity<ApiResponse<?>> changeStudyRoomMemberAuthority(@PathVariable Long studyRoomId, @Valid @RequestBody ChangeStudyRoomAuthorityRequestDto changeStudyRoomAuthorityRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {
        boolean isChangedToManagerAuthority = studyRoomService.changeStudyRoomMemberAuthority(userAdapter, studyRoomId, changeStudyRoomAuthorityRequestDto);

        if(isChangedToManagerAuthority){
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucess("성공적으로 관리자권한을 부여하였습니다."));
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucess("성공적으로 관리자권한을 해제하였습니다."));
        }
    }

    @Operation(summary = "스터디방 멤버 조회 API", description = "스터디방 멤버 조회 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "접근 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping("/{studyRoomId}/members")
    public ResponseEntity<ApiResponse<StudyRoomMemberListResponseDto>> setStudyMemberToManager(@PathVariable Long studyRoomId, @Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) throws ParseException {
        StudyRoomMemberListResponseDto studyRoomMemberList = studyRoomService.getStudyRoomMemberList(userAdapter, studyRoomId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucessWithDataAndMessage(studyRoomMemberList, "멤버를 성공적으로 조회했습니다."));
    }

    @Operation(summary = "스터디방 멤버 여부 확인 API", description = "스터디방 멤버 여부 확인 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "접근 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping("/{studyRoomId}/is-member")
    public ResponseEntity<ApiResponse<?>> checkIfStudyRoomMember(@PathVariable Long studyRoomId, @Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {
        studyRoomService.throwExceptionIfNotStudyRoomMember(userAdapter, studyRoomId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucess("현재 사용자는 스터디방 멤버입니다."));
    }

    @Operation(summary = "스터디방 관리자 여부 확인 API", description = "스터디방 관리자 여부 확인 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "접근 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })

    @PostMapping("/{studyRoomId}/is-manager")
    public ResponseEntity<ApiResponse<?>> checkIfStudyRoomManager(@PathVariable Long studyRoomId, @Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {
        studyRoomService.throwExceptionIfNotStudyRoomManager(userAdapter, studyRoomId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucess("현재 사용자는 스터디방 관리자 입니다."));
    }
    @Operation(summary = "스터디방 사용 언어 조회 API", description = "스터디방 사용 언어 조회 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "접근 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping("/{studyRoomId}/programming-languages")
    public ResponseEntity<ApiResponse<ProgrammingLanguageListResponseDto>> getStudyRoomProgrammingLanguage(@PathVariable Long studyRoomId, @Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) throws ParseException {
        ProgrammingLanguageListResponseDto studyRoomProgrammingLanguageList = studyRoomService.getStudyRoomProgrammingLanguageList(studyRoomId);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucessWithDataAndMessage(studyRoomProgrammingLanguageList, "스터디방의 사용 언어 목록을 성공적으로 조회했습니다."));
    }

    //<<<<<<< HEAD
//    @Operation(summary = "스터디방 컴파일 API", description = "스터디방 컴파일 API입니다.")
//    @ApiResponses(value = {
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
//            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "접근 실패", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
//    })
//    @PostMapping("/{studyRoomId}/compile")
//    public ResponseEntity<ApiResponse<?>> compileSourceCode(@PathVariable Long studyRoomId, @Valid @RequestBody CompileSourceCodeRequestForm compileSourceCodeRequestForm, @Parameter(hidden = true) @AuthenticationPrincipal UserAdapter userAdapter) {
//
//        Object o = compileService.compileSourceCode(studyRoomId, compileSourceCodeRequestForm, userAdapter);
//        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucessWithDataAndMessage(o, "테스트"));
//    }
//=======
}
