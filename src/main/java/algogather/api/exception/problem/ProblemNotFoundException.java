package algogather.api.exception.problem;

public class ProblemNotFoundException extends RuntimeException{

    public ProblemNotFoundException(String message) {
        super(message);
    }
}
