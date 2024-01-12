package exceptions;

// Thrown when user enters a wrong input or input types
public class IllegalInputException extends Exception {
    public IllegalInputException(String str) {
        super(str);
    }
}
