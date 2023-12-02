public class Electronic extends Product {
    private String brand;
    private int warrantyPeriod;

    public Electronic(String id, String name, double price, int count, String brand, int warrantyPeriod) {
        super(id, name, price, count);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }
}
