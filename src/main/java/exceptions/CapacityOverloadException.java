package exceptions;

public class CapacityOverloadException extends Exception {
    public CapacityOverloadException() {
        super("Inventory Capacity Exceeded!");
    }
    
}
