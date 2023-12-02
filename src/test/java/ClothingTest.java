import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.*;

public class ClothingTest {
    @Test
    public void clothingClassExists() {
        try {
            Class.forName("Clothing");
        } catch (Exception e) {
            Assert.fail("'Clothing' class was not found!");
        }
    }

    @Test
    public void clothingInheritsFromProduct() {
        assertEquals(Product.class, Clothing.class.getSuperclass());
    }

    @Test
    public void clothingEqualsMethodWorks() {
        Clothing clothing = new Clothing(
            "abcd1234",
            "T-Shirt",
            399.99,
            15,
            "XL",
            "White"
        );

        Clothing other = new Clothing(
            "abcd1234",
            "T-Shirt",
            399.99,
            15,
            "XL",
            "White"
        );

        Clothing another = new Clothing(
            "xyz123",
            "Jeans",
            499.96,
            1,
            "L",
            "Black"
        );

        assertTrue(clothing.equals(other));
        assertFalse(clothing.equals(another));
    }

    @Test
    public void clothingToStringMethodWorks() {
        Clothing clothing = new Clothing(
            "abcd1234",
            "T-Shirt",
            399.99,
            15,
            "XL",
            "White"
        );

        assertTrue(clothing.toString().equals("T-Shirt"));
    }
}
