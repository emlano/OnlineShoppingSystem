package exceptions;

// Thrown when inventory limit has reached
public class CapacityOverloadException extends Exception {
    public CapacityOverloadException() {
        super("Inventory Capacity Exceeded!");
    }
    
}
