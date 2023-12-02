import org.junit.*;
import org.junit.jupiter.api.DisplayName;
import static org.junit.Assert.*;

@DisplayName("Testing class Electronic")
public class ElectronicTest {

    @DisplayName("Electronic class must exist in Package")
    @Test
    public void electronicClassExists() {
        try {
            Class.forName("Electronic");
        } catch (Exception e) {
            Assert.fail("'Electronic' class was not found!");
        }
    }

    @DisplayName("Electronic class must inherit from class Product")
    @Test
    public void electronicInheritsFromProduct() {
        assertSame(Electronic.class.getSuperclass(), Product.class);
    }

    @DisplayName("Testing Electronic class's Getters")
    @Test
    public void electronicGettersWork() {
        Electronic electronic = new Electronic("Toshiba", 3);

        assertEquals("Toshiba", electronic.getBrand());
        assertEquals(3, electronic.getWarrantyPeriod());
    }

    @DisplayName("Testing Electronic class's Setters")
    @Test
    public void electronicSettersWork() {
        Electronic electronic = new Electronic("Toshiba", 3);

        electronic.setBrand("Mitsubishi");
        electronic.setWarrantyPeriod(1);

        assertEquals("Mitsubishi", electronic.getBrand());
        assertEquals(1, electronic.getWarrantyPeriod());
    }

    @DisplayName("Testing equals method of Electronic class")
    @Test
    public void electronicEqualsMethodWorks() {
        Electronic electronic = new Electronic("Toshiba", 3);
        Electronic other = new Electronic("Toshiba", 3);
        Electronic another = new Electronic("Mitsubishi", 1);

        assertEquals(electronic, other);
        assertNotEquals(electronic, another);
    }
}
