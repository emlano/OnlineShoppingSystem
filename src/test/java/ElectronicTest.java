import org.junit.*;
import static org.junit.Assert.*;

public class ElectronicTest {

    @Test
    public void electronicClassExists() {
        try {
            Class.forName("Electronic");
        } catch (Exception e) {
            Assert.fail("'Electronic' class was not found!");
        }
    }

    @Test
    public void electronicInheritsFromProduct() {
        assertTrue(Electronic.class.getSuperclass() == Product.class);
    }

    @Test
    public void electronicGettersWork() {
        Electronic electronic = new Electronic("Toshiba", 3);

        assertTrue(electronic.getBrand().equals("Toshiba"));
        assertEquals(3, electronic.getWarrantyPeriod());
    }

    @Test
    public void electronicSettersWork() {
        Electronic electronic = new Electronic("Toshiba", 3);

        electronic.setBrand("Mitsubishi");
        electronic.setWarrantyPeriod(1);

        assertTrue(electronic.getBrand().equals("Mitsubishi"));
        assertEquals(1, electronic.getWarrantyPeriod());
    }

    @Test
    public void electronicEqualsMethodWorks() {
        Electronic electronic = new Electronic("Toshiba", 3);
        Electronic other = new Electronic("Toshiba", 3);
        Electronic another = new Electronic("Mitsubishi", 1);

        assertEquals(electronic, other);
        assertNotEquals(electronic, another);
    }
}
