package exceptions;

public class NonUniqueProductIdException extends Exception {
    public NonUniqueProductIdException() {
        super("Product Id already in use! Id must be unique!");
    }
}
