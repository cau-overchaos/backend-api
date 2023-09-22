package algogather.api.domain.problem;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProblemProvider {
    BAEKJOON("BAEKJOON", "백준"), LEETCODE("LEETCODE", "LEETCODE");

    private final String key;
    private final String value;
}
