package algogather.api.dto.api;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {
    private static final String SUCCESS_STATUS = "success";
    private static final String FAIL_STATUS = "fail";
    private static final String ERROR_STATUS = "error";

    private String status;
    private T data;
    private String message;

    public static <T> ApiResponse<T> createSuccess(T data) {
        return new ApiResponse<>(SUCCESS_STATUS, data, null);
    }

    public static ApiResponse<?> createSucessWithNoContent() {
        return new ApiResponse<>(SUCCESS_STATUS, null, null);
    }

    public static ApiResponse<?> sucess(String message) {
        return new ApiResponse<>(SUCCESS_STATUS, null, message);
    }

    /**
     * Validator에 의해 유효하지 않은 데이터로 인해 API 호출이 거부될 때 반환
     */
    public static ApiResponse<?> createFail(BindingResult bindingResult) {
        Map<String, Object> errors = new HashMap<>();

        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for(ObjectError error : allErrors) {
            if (error instanceof FieldError) {
                errors.put(((FieldError) error).getField(), error.getDefaultMessage());
            } else {
                errors.put(error.getObjectName(), error.getDefaultMessage());
            }
        }
        return new ApiResponse<>(FAIL_STATUS, errors, null);
    }

    /**
     * 예외 발생으로 API 호출 실패시 반환
     */
    public static ApiResponse<?> createError(String message) {
        return new ApiResponse<>(ERROR_STATUS, null, message);
    }

    public ApiResponse(String status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
