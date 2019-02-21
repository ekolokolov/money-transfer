package ea.kolokolov.exception;

public class SourceNotFoundException extends RuntimeException {

    private static String MESSAGE = "Sorry, source not found";

    public SourceNotFoundException() {
        super(MESSAGE);
    }
}
