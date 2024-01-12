package lib;

import enums.Size;

public class Clothing extends Product {
    private Size size;
    private String color;

    // Constructor with all attributes
    public Clothing(String id, String name, double price, int count, Size size, String color) {
        super(id, name, price, count);
        this.size = size;
        this.color = color;
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

    public void setSize(Size size) {
        this.size = size;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Clothing c = (Clothing) super.clone();
        
        c.color = color;
        c.size = size;

        return c;
    }

    @Override
    public boolean equals(Object obj) {
        // Calls parent class's equals method to check inherited vars
        if (!super.equals(obj)) return false;

        // Type casting object type 'Object' to 'lib.Clothing'
        Clothing clothing = (Clothing) obj;

        if (!this.color.equals(clothing.color)) return false;

        return this.size.equals(clothing.size);
    }
}

