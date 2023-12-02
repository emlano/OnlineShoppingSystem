public abstract class Product {
    private String id;
    private String name;
    private double price;
    private int count;

    public Product(String id, String name, double price, int count) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public double getPrice() {
        return price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        
        if (obj.getClass() != this.getClass()) {
            System.out.println(this.getClass());
            return false;
        }

        Product product = (Product) obj;

        if (!this.id.equals(product.id)) return false;
        
        if (!this.name.equals(product.name)) return false;

        if (this.count != product.count) return false;

        if (this.price != product.price) return false;

        return true;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
