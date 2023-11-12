package algogather.api.exception.handler;

import algogather.api.dto.api.ApiResponse;
import algogather.api.exception.recruit.RecruitPostNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RecruitExceptionHandler {
    @ExceptionHandler(RecruitPostNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleRecruitPostNotFoundException(RecruitPostNotFoundException exception) {
        log.debug("RecruitPostNotFoundException = {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createError(exception.getMessage()));
    }
}
