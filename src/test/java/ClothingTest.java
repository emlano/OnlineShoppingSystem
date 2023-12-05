import org.junit.*;
import org.junit.jupiter.api.DisplayName;
import static org.junit.Assert.*;

@DisplayName("Testing class Clothing")
public class ClothingTest {

    @Test
    @DisplayName("Clothing class must exist in Package")
    public void clothingClassExists() {
        try {
            Class.forName("Clothing");
        } catch (Exception e) {
            Assert.fail("'Clothing' class was not found!");
        }
    }

    @Test
    @DisplayName("Clothing class must inherit from class Product")
    public void clothingInheritsFromProduct() {
        assertEquals(Product.class, Clothing.class.getSuperclass());
    }

    @Test
    @DisplayName("Testing Clothing class's Getters")
    public void clothingGettersWork() {
        Clothing clothing = new Clothing(Size.M, "White");

        assertEquals(Size.M, clothing.getSize());
        assertEquals("White", clothing.getColor());
    }

    @Test
    @DisplayName("Testing Clothing class's Setters")
    public void clothingSettersWork() {
        Clothing clothing = new Clothing(Size.L, "White");

        clothing.setColor("Indigo");
        clothing.setSize(Size.M);

        assertEquals("Indigo", clothing.getColor());
        assertEquals(Size.M, clothing.getSize());
    }

    @Test
    @DisplayName("Testing if the equals method is working")
    public void clothingEqualsMethodWorks() {
        Clothing clothing = new Clothing(Size.XL, "White");
        Clothing other = new Clothing(Size.XL, "White");
        Clothing another = new Clothing(Size.L, "Black");

        assertEquals(clothing, other);
        assertNotEquals(clothing, another);
    }
}
