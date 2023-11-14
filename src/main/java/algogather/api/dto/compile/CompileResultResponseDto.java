package algogather.api.dto.compile;

import lombok.Getter;

@Getter
public class CompileResultResponseDto {
    private String resultType;
    private Result result;

    @Getter
    static class Result {
        private String cpu_time;
        private String errorDescription;
        private String exit_code;
        private String output;
        private String user_time;
    }
}
