package exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("No such user found!");
    }
}
