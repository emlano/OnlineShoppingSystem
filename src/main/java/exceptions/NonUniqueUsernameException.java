package exceptions;

// Thrown when a user sign happens with an already existing users username
public class NonUniqueUsernameException extends Exception {
    public NonUniqueUsernameException() {
        super("Username already used! Usernames must be unique!");
    }
}
