package exceptions;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException() {
        super("No such product found!");
    }
}
