package exceptions;

public class NonUniqueUsernameException extends Exception {
    public NonUniqueUsernameException() {
        super("Username already used! Usernames must be unique!");
    }
}
