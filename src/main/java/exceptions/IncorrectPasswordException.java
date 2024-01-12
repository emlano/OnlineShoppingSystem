package exceptions;

// Thrown when a log in is attempted with a wrong password
public class IncorrectPasswordException extends Exception {
    public IncorrectPasswordException() {
        super("Login information was not correct!");
    }
}
