package algogather.api.service.recruit;

import algogather.api.domain.recruit.RecruitCommentRepository;
import algogather.api.domain.recruit.RecruitPost;
import algogather.api.domain.recruit.RecruitPostRepository;
import algogather.api.domain.studyroom.StudyRoom;
import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.recruit.CreateRecruitPostRequestForm;
import algogather.api.dto.recruit.CreatedRecruitPostResponseDto;
import algogather.api.service.studyroom.StudyRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecruitService {
    private final StudyRoomService studyRoomService;

    private final RecruitPostRepository recruitPostRepository;
    private final RecruitCommentRepository recruitCommentRepository;

    public CreatedRecruitPostResponseDto saveRecruitPost(CreateRecruitPostRequestForm createRecruitPostRequestForm, UserAdapter userAdapter){
        studyRoomService.throwExceptionIfNotStudyRoomManager(userAdapter, createRecruitPostRequestForm.getStudyRoomId()); // 해당 스터디룸 관리자만 홍보글을 작성할 수 있다.

        StudyRoom studyRoom = studyRoomService.findById(createRecruitPostRequestForm.getStudyRoomId());

        RecruitPost recruitPost = RecruitPost.builder()
                .title(createRecruitPostRequestForm.getTitle())
                .text(createRecruitPostRequestForm.getText())
                .dueDate(createRecruitPostRequestForm.getDueDate())
                .studyRoom(studyRoom)
                .user(userAdapter.getUser())
                .build();

        RecruitPost savedRecruitPost = recruitPostRepository.save(recruitPost);

        return CreatedRecruitPostResponseDto.builder()
                .title(savedRecruitPost.getTitle())
                .text(savedRecruitPost.getText())
                .dueDate(savedRecruitPost.getDueDate())
                .studyRoomInfoResponseDto(
                        studyRoomService.getStudyRoomInfo(savedRecruitPost.getStudyRoom().getId())
                )
                .createdAt(recruitPost.getCreatedAt())
                .updatedAt(recruitPost.getUpdatedAt()).build();
    }
}
