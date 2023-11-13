package algogather.api.dto.recruit;

import algogather.api.dto.studyroom.StudyRoomInfoResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class CreatedRecruitPostResponseDto {
    private String title;

    private String text;

    private StudyRoomInfoResponseDto studyRoomInfoResponseDto;

    private LocalDate dueDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder

    public CreatedRecruitPostResponseDto(String title, String text, StudyRoomInfoResponseDto studyRoomInfoResponseDto, LocalDate dueDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.text = text;
        this.studyRoomInfoResponseDto = studyRoomInfoResponseDto;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
