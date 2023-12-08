package exceptions;

public class IllegalInputException extends Exception {
    public IllegalInputException(String str) {
        super(str);
    }

    public IllegalInputException() {
        this("Illegal input entered!");
    }
}
