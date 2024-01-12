package exceptions;

// Thrown when a product cannot be added to cart cause of product count being 0
public class NotEnoughProductStockException extends Exception {
    public NotEnoughProductStockException() {
        super("Product stock too low to satisfy this request!");
    }
}