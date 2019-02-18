package ea.kolokolov.exception;

public class UserNotFoundException extends RuntimeException {

    private static String MESSAGE = "Sorry, account: %s not found";

    public UserNotFoundException(String login) {
        super(String.format(MESSAGE, login));
    }

}
