package algogather.api.controller.studyroom;

import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.api.ApiResponse;
import algogather.api.service.studyroom.StudyRoomService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/studyrooms")
public class StudyRoomController {

    private final StudyRoomService studyRoomService;
//    @Operation(summary = "스터디룸 테스트중!!", description = "스터디룸 테스트중!!")
//    @GetMapping
//    public ResponseEntity<ApiResponse<?>> test (@AuthenticationPrincipal UserAdapter userAdapter) {
//        studyRoomService.checkStudyRoomMember(userAdapter);
//        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.sucess("test 성공!"));
//    }
}
