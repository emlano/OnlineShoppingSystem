package exceptions;

// Thrown when product id input is already used to register a different product with the system
public class NonUniqueProductIdException extends Exception {
    public NonUniqueProductIdException() {
        super("Product Id already in use! Id must be unique!");
    }
}
