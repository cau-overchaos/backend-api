package algogather.api.dto.studyroom;

import lombok.Getter;

@Getter
public class AddStudyRoomMemberResponseDto {
    private Long studyRoomId;
    private String addedUserId;

    public AddStudyRoomMemberResponseDto(Long studyRoomId, String addedUserId) {
        this.studyRoomId = studyRoomId;
        this.addedUserId = addedUserId;
    }
}
