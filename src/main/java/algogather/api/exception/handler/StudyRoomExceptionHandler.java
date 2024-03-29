package algogather.api.exception.handler;

import algogather.api.dto.api.ApiResponse;
import algogather.api.exception.async.AsyncException;
import algogather.api.exception.problem.NotSupportedProviderException;
import algogather.api.exception.studyroom.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class StudyRoomExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> handleAlreadyExistingStudyRoomMemberException(AlreadyExistingStudyRoomMemberException exception) {
        log.debug("AlreadyExistingStudyRoomMemberException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> handleChangeMyStudyRoomAuthorityException(ChangeMyStudyRoomAuthorityException exception) {
        log.debug("ChangeMyStudyRoomAuthorityException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> handleDeleteManagerFromStudyRoomException(DeleteManagerFromStudyRoomException exception) {
        log.debug("DeleteManagerFromStudyRoomException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> handleDeleteMeFromStudyRoomException(DeleteMeFromStudyRoomException exception) {
        log.debug("DeleteMeFromStudyRoomException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> handleNotStudyRoomManagerException(NotStudyRoomManagerException exception) {
        log.debug("NotStudyRoomManagerException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> handleNotStudyRoomMemberException(NotStudyRoomMemberException exception) {
        log.debug("NotStudyRoomMemberException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> handleStudyRoomNotFoundException(StudyRoomNotFoundException exception) {
        log.debug("StudyRoomNotFoundException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> handleAsyncException(AsyncException exception) {
        log.debug("AsyncException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.createError(exception.getMessage()));
    }
}
