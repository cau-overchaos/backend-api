package algogather.api.service.recruit;

import algogather.api.domain.recruit.RecruitComment;
import algogather.api.domain.recruit.RecruitCommentRepository;
import algogather.api.domain.recruit.RecruitPost;
import algogather.api.domain.recruit.RecruitPostRepository;
import algogather.api.domain.studyroom.StudyRoom;
import algogather.api.domain.user.UserAdapter;
import algogather.api.dto.recruit.*;
import algogather.api.exception.recruit.RecruitPostNotFoundException;
import algogather.api.service.studyroom.StudyRoomService;
import algogather.api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitService {
    private final StudyRoomService studyRoomService;
    private final UserService userService;

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

    public RecruitPostListResponseDto getAllRecruitPostList() {
        List<RecruitPost> recruitPostList = recruitPostRepository.findAllByOrderByIdDesc();
        List<RecruitPostDto> recruitPostDtoList = new ArrayList<>();
        for (RecruitPost recruitPost : recruitPostList) {

            RecruitPostDto recruitPostDto = RecruitPostDto.builder()
                    .id(recruitPost.getId())
                    .title(recruitPost.getTitle())
                    .writerUserId(recruitPost.getUser().getUserId())
                    .writerUserName(recruitPost.getUser().getName())
                    .build();
            recruitPostDtoList.add(recruitPostDto);
        }

        return new RecruitPostListResponseDto(recruitPostDtoList);
    }

    public ExistingRecruitPostResponseDto getExistingRecruitPostResponseDto(Long recruitPostId) {
        RecruitPost recruitPost = findRecruitPostById(recruitPostId);

        return ExistingRecruitPostResponseDto.builder()
                .title(recruitPost.getTitle())
                .text(recruitPost.getText())
                .dueDate(recruitPost.getDueDate())
                .studyRoomInfoResponseDto(
                        studyRoomService.getStudyRoomInfo(recruitPost.getStudyRoom().getId())
                )
                .createdAt(recruitPost.getCreatedAt())
                .updatedAt(recruitPost.getUpdatedAt()).build();
    }

    public RecruitPost findRecruitPostById(Long recruitPostId) {
        return recruitPostRepository.findById(recruitPostId).orElseThrow(RecruitPostNotFoundException::new);
    }

    public CreatedRecruitCommentResponseDto saveRecruitComment(Long recruitPostId, CreateRecruitCommentRequestForm createRecruitCommentRequestForm, UserAdapter userAdapter){
        RecruitPost recruitPost = findRecruitPostById(recruitPostId);

        RecruitComment newRecruitComment = RecruitComment.builder()
                .text(createRecruitCommentRequestForm.getComment())
                .recruitPost(recruitPost)
                .user(userAdapter.getUser())
                .build();

        RecruitComment savedRecruitComment = recruitCommentRepository.save(newRecruitComment);

        return CreatedRecruitCommentResponseDto.builder()
                .id(savedRecruitComment.getId())
                .comment(savedRecruitComment.getText())
                .writerUserId(savedRecruitComment.getUser().getUserId())
                .writerUserName(savedRecruitComment.getUser().getName())
                .createdAt(savedRecruitComment.getCreatedAt())
                .updatedAt(savedRecruitComment.getUpdatedAt())
                .recruitPostId(savedRecruitComment.getRecruitPost().getId())
                .build();
    }

}
