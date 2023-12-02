public class Electronic extends Product {
    private String brand;
    private int warrantyPeriod;

    // Constructor with all attributes
    public Electronic(String id, String name, double price, int count, String brand, int warrantyPeriod) {
        super(id, name, price, count);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    // Constructor with instance attributes defaulted
    public Electronic(String id, String name, double price, int count) {
        this(id, name, price, count, "DefaultBrand", 1);
    }

    // Constructor with instance attributes and item count defaulted
    public Electronic(String id, String name, double price) {
        this(id, name, price, 1);
    }

    // Constructor with inherited attributes defaulted
    public Electronic(String brand, int warrantyPeriod) {
        this("DefaultId", "DefaultName", 100, 1, brand, warrantyPeriod);
    }

    public String getBrand() {
        return brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public boolean equals(Object obj) {
        // Calls parent class's equals method to check inherited attributes
        if (!super.equals(obj)) return false;

        // Type casting object type 'Object' to 'Electronic'
        Electronic electronic = (Electronic) obj;

        if (!this.brand.equals(electronic.brand)) return false;

        if (this.warrantyPeriod != electronic.warrantyPeriod) return false;

        return true;
    }
}
