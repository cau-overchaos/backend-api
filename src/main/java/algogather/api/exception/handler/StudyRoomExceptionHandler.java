package algogather.api.exception.handler;

import algogather.api.dto.api.ApiResponse;
import algogather.api.exception.NotStudyRoomManagerException;
import algogather.api.exception.NotStudyRoomMemberException;
import algogather.api.exception.StudyRoomNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class StudyRoomExceptionHandler {
    @ExceptionHandler(NotStudyRoomMemberException.class)
    public ResponseEntity<ApiResponse<?>> handleNotStudyRoomMemberException(NotStudyRoomMemberException exception) {
        log.debug("NotStudyRoomMemberException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler(NotStudyRoomManagerException.class)
    public ResponseEntity<ApiResponse<?>> handleNotStudyRoomManagerException(NotStudyRoomManagerException exception) {
        log.debug("NotStudyRoomManagerException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler(StudyRoomNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleStudyRoomNotFoundException(StudyRoomNotFoundException exception) {
        log.debug("StudyRoomNotFoundException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(exception.getMessage()));
    }
}
