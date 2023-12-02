public class Clothing extends Product {
    private String size;
    private String color;

    public Clothing(String id, String name, double price, int count, String size, String color) {
        super(id, name, price, count);
        this.size = size;
        this.color = color;
    }

    public Clothing(String id, String name, double price, int count) {
        this(id, name, price, count, "M", "Black");
    }

    public Clothing(String id, String name, double price) {
        this(id, name, price, 1);
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;

        Clothing clothing = (Clothing) obj;

        if (!this.color.equals(clothing.color)) return false;

        if (!this.size.equals(clothing.size)) return false;

        return true;
    }
}
