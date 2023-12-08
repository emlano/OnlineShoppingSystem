import lib.Clothing;
import lib.Product;
import org.junit.*;
import org.junit.jupiter.api.DisplayName;
import static org.junit.Assert.*;

@DisplayName("Testing class lib.Clothing")
public class ClothingTest {

    @Test
    @DisplayName("lib.Clothing class must exist in Package")
    public void clothingClassExists() {
        try {
            Class.forName("lib.Clothing");
        } catch (Exception e) {
            Assert.fail("'lib.Clothing' class was not found!");
        }
    }

    @Test
    @DisplayName("lib.Clothing class must inherit from class lib.Product")
    public void clothingInheritsFromProduct() {
        assertEquals(Product.class, Clothing.class.getSuperclass());
    }

    @Test
    @DisplayName("Testing lib.Clothing class's Getters")
    public void clothingGettersWork() {
        Clothing clothing = new Clothing("M", "White");

        assertEquals("M", clothing.getSize());
        assertEquals("White", clothing.getColor());
    }

    @Test
    @DisplayName("Testing lib.Clothing class's Setters")
    public void clothingSettersWork() {
        Clothing clothing = new Clothing("L", "White");

        clothing.setColor("Indigo");
        clothing.setSize("M");

        assertEquals("Indigo", clothing.getColor());
        assertEquals("M", clothing.getSize());
    }

    @Test
    @DisplayName("Testing if the equals method is working")
    public void clothingEqualsMethodWorks() {
        Clothing clothing = new Clothing("XL", "White");
        Clothing other = new Clothing("XL", "White");
        Clothing another = new Clothing("L", "Black");

        assertEquals(clothing, other);
        assertNotEquals(clothing, another);
    }
}
