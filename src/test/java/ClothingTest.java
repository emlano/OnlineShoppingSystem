import org.junit.*;
import static org.junit.Assert.*;

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
    public void clothingGettersWork() {
      Clothing clothing = new Clothing("XL", "White");

      assertTrue(clothing.getSize().equals("XL"));
      assertTrue(clothing.getColor().equals("White"));
    }

    @Test
    public void clothingSettersWork() {
       Clothing clothing = new Clothing("XL", "White");

       clothing.setColor("Indigo");
       clothing.setSize("M");

       assertTrue(clothing.getColor().equals("Indigo"));
       assertTrue(clothing.getSize().equals("M"));
    }

    @Test
    public void clothingEqualsMethodWorks() {
        Clothing clothing = new Clothing("XL", "White");
        Clothing other = new Clothing("XL", "White");
        Clothing another = new Clothing("L", "Black");

        assertEquals(clothing, other);
        assertNotEquals(clothing, another);
    }
}
