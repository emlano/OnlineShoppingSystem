package exceptions;

// Thrown when non existing user attempts to sign in
public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("No such user found!");
    }
}
