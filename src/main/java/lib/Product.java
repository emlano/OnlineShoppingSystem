package lib;

import exceptions.NotEnoughProductStockException;

public abstract class Product implements Cloneable {
    private String id;
    private String name;
    private double price;
    private int count;

    // Constructor
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

    public Product extract(int count) throws CloneNotSupportedException, NotEnoughProductStockException {
        Product p = (Product) this.clone();

        if (p.count < count) {
            throw new NotEnoughProductStockException();
        }

        p.setCount(1);
        this.count -= count;

        return p;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Product p = (Product) super.clone();

        p.count = this.count;
        p.id = this.id;
        p.name = this.name;
        p.price = this.price;

        return p;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        
        // Check if classes match the passed object
        if (obj.getClass() != this.getClass()) return false;

        // Type casting object type 'Object' to 'lib.Product'
        Product product = (Product) obj;

        if (!this.id.equals(product.id)) return false;
        
        if (!this.name.equals(product.name)) return false;

        if (this.price != product.price) return false;

        return true;
    }

    @Override
    // Returns lib.Product name when printed
    public String toString() {
        return this.name;
    }
}
