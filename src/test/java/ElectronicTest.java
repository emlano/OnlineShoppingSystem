import lib.Electronic;
import lib.Product;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Testing class Electronic")
public class ElectronicTest {

    @Test
    @DisplayName("Electronic class must exist in Package")
    public void electronicClassExists() {
        try {
            Class.forName("lib.Electronic");
        } catch (Exception e) {
            Assertions.fail("'Electronic' class was not found!");
        }
    }

    @Test
    @DisplayName("Electronic class must inherit from class Product")
    public void electronicInheritsFromProduct() {
        assertSame(Electronic.class.getSuperclass(), Product.class);
    }

    @Test
    @DisplayName("Testing Electronic class's Getters")
    public void electronicGettersWork() {
        Electronic electronic = new Electronic("Toshiba", 3);

        assertEquals("Toshiba", electronic.getBrand());
        assertEquals(3, electronic.getWarrantyPeriod());
    }

    @Test
    @DisplayName("Testing Electronic class's Setters")
    public void electronicSettersWork() {
        Electronic electronic = new Electronic("Toshiba", 3);

        electronic.setBrand("Mitsubishi");
        electronic.setWarrantyPeriod(1);

        assertEquals("Mitsubishi", electronic.getBrand());
        assertEquals(1, electronic.getWarrantyPeriod());
    }

    @Test
    @DisplayName("Testing equals method of Electronic class")
    public void electronicEqualsMethodWorks() {
        Electronic electronic = new Electronic("Toshiba", 3);
        Electronic other = new Electronic("Toshiba", 3);
        Electronic another = new Electronic("Mitsubishi", 1);

        assertEquals(electronic, other);
        assertNotEquals(electronic, another);
    }
}
