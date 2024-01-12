package exceptions;

// Thrown when a input given is outside of bounds specified by the program
public class OutOfBoundsException extends Exception {
    public OutOfBoundsException(String str) {
        super(str);
    }
}
