public class Clothing extends Product {
    private Size size;
    private String color;

    // Constructor with all attributes
    public Clothing(String id, String name, double price, int count, Size size, String color) {
        super(id, name, price, count);
        this.size = size;
        this.color = color;
    }

    // Constructor with instance attributes being defaulted
    public Clothing(String id, String name, double price, int count) {
        this(id, name, price, count, Size.M, "Black");
    }

    // Constructor with instance attributes and item count being defaulted
    public Clothing(String id, String name, double price) {
        this(id, name, price, 1);
    }

    // Constructor with inherited attributes being defaulted.
    public Clothing(Size size, String color) {
        this("DefaultId", "DefaultName", 100, 1, size, color);
    }

    public String getColor() {
        return color;
    }

    public Size getSize() {
        return size;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setSize(String size) {
        switch (size.toUpperCase()) {
            case "XS" -> this.size = Size.XS;
            case "S" -> this.size = Size.S;
            case "M" -> this.size = Size.M;
            case "L" -> this.size = Size.L;
            case "XL" -> this.size = Size.XL;
            case "XLL" -> this.size = Size.XXL;
        }
    }

    @Override
    public boolean equals(Object obj) {
        // Calls parent class's equals method to check inherited vars
        if (!super.equals(obj)) return false;

        // Type casting object type 'Object' to 'Clothing'
        Clothing clothing = (Clothing) obj;

        if (!this.color.equals(clothing.color)) return false;

        if (!this.size.equals(clothing.size)) return false;

        return true;
    }
}

