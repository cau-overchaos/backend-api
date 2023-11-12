package algogather.api.dto.recruit;

import algogather.api.domain.studyroom.StudyRoom;
import algogather.api.domain.studyroom.StudyRoomVisibility;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

////TODO 구인 게시글 목록 반환
//@Getter
//public class RecruitPostListResponseDto {
//    private List<StudyRoomDto> studyRoomList;
//
//    public RecruitPostListResponseDto(List<StudyRoom> studyRoomList) {
//        this.studyRoomList = studyRoomList.stream()
//                .map(studyRoom -> new StudyRoomDto(studyRoom)).collect(Collectors.toList());
//    }
//
//    @Getter
//    @AllArgsConstructor
//    public static class RecruitPostDto {
//        private Long id;
//        private String title;
//        private Integer curUserCnt;
//        private Integer maxUserCnt;
//        private String writerUserId;
//        private String writerUserName;
//
//        public RecruitPostDto(Long id, String title, Integer curUserCnt, Integer maxUserCnt, String writerUserId, String writerUserName) {
//            this.id = id;
//            this.title = title;
//            this.curUserCnt = curUserCnt;
//            this.maxUserCnt = maxUserCnt;
//            this.writerUserId = writerUserId;
//            this.writerUserName = writerUserName;
//        }
//    }
//}
