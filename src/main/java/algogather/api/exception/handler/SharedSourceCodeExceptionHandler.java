package algogather.api.exception.handler;

import algogather.api.dto.api.ApiResponse;
import algogather.api.exception.sharedsourcecode.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class SharedSourceCodeExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> handleSharedSourceCodeNotFoundException(SharedSourceCodeNotFoundException exception) {
        log.debug("SharedSourceCodeNotFoundException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> handleSharedSourceCodeAndStudyRoomNotMatchingException(SharedSourceCodeAndStudyRoomNotMatchingException exception) {
        log.debug("SharedSourceCodeAndStudyRoomNotMatchingException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> handleSharedSourceCodeAndFeedbackNotMatchingException(SharedSourceCodeAndFeedbackNotMatchingException exception) {
        log.debug("SharedSourceCodeAndFeedbackNotMatchingException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> handleLineNumberExceedTotalLineCountException(LineNumberExceedTotalLineCountException exception) {
        log.debug("LineNumberExceedTotalLineCountException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> handleFeedbackNotFoundException(FeedbackNotFoundException exception) {
        log.debug("FeedbackNotFoundException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> handleNotSameSourceCodeException(NotSameSourceCodeException exception) {
        log.debug("NotSameSourceCodeException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> handleNotSameLineNumberException(NotSameLineNumberException exception) {
        log.debug("NotSameLineNumberException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> handleNotFeedbackWriterException(NotFeedbackWriterException exception) {
        log.debug("NotFeedbackWriterException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> handleAlreadyDeletedFeedback(AlreadyDeletedFeedback exception) {
        log.debug("AlreadyDeletedFeedback = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(exception.getMessage()));
    }
}
