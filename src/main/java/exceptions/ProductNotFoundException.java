package exceptions;

// Thrown when a non existing product was requested from the program
public class ProductNotFoundException extends Exception {
    public ProductNotFoundException() {
        super("No such product found!");
    }
}
