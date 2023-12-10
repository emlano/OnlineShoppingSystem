package exceptions;

public class IncorrectPasswordException extends Exception {
    public IncorrectPasswordException() {
        super("Login information was not correct!");
    }
}
