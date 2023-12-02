import org.junit.*;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;

@DisplayName("Testing class Product")
public class ProductTest {

    @DisplayName("Product class must exist")
    @Test
    public void productClassExists() {
        try {
            Class.forName("Product");
        } catch (Exception e) {
            Assert.fail("'Product' class was not found!");
        }
    }

    @DisplayName("Testing Product class's Getters")
    @Test
    public void productGettersWork() {
        SubProduct sb = new SubProduct("abcdef", "SubProduct", 199.99, 10);
        
        assertEquals("abcdef", sb.getId());
        assertEquals("SubProduct", sb.getName());
        assertEquals(199.99, sb.getPrice(), 0.0);
        assertEquals(10, sb.getCount());
    }

    @DisplayName("Testing Product class's Setters")
    @Test
    public void productSettersWork() {
        SubProduct sb = new SubProduct("abcdef", "SubProduct", 199.99, 10);

        sb.setId("defabc");
        sb.setName("ProductSub");
        sb.setPrice(399.99);
        sb.setCount(1);

        assertEquals("defabc", sb.getId());
        assertEquals("ProductSub", sb.getName());
        assertEquals(399.99, sb.getPrice(), 0.0);
        assertEquals(1, sb.getCount());
    }

    @DisplayName("Testing equals method of Product class")
    @Test
    public void productEqualsMethodWorks() {
        SubProduct sb = new SubProduct("abcdef", "SubProduct", 199.99, 10);
        SubProduct other = new SubProduct("abcdef", "SubProduct", 199.99, 10);
        SubProduct another = new SubProduct("defghk", "SubProduct", 199.99, 11);

        assertEquals(sb, other);
        assertNotEquals(sb, another);
    }

    @DisplayName("Testing toString method of Product class")
    @Test
    public void productToStringMethodWorks() {
        SubProduct sb = new SubProduct("abcdef", "SubProduct", 199.99, 10);

        assertEquals("SubProduct", sb.toString());
    }
}

class SubProduct extends Product {
    public SubProduct(String id, String name, double price, int count) {
        super(id, name, price, count);
    }
}
