package ea.kolokolov.exception;

public class OperationForbiddenException extends RuntimeException {

    private static String MESSAGE = "Sorry, operation forbidden. Cause: %s";

    public OperationForbiddenException(String cause) {
        super(String.format(MESSAGE, cause));
    }

}
