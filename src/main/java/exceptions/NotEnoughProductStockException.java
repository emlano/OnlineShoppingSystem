package exceptions;

public class NotEnoughProductStockException extends Exception {
    public NotEnoughProductStockException() {
        super("Product stock too low to satisfy this request!");
    }
}