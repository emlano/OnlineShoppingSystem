import org.junit.*;
import static org.junit.Assert.*;

public class ProductTest {

    @Test
    public void productClassExists() {
        try {
            Class.forName("Product");
        } catch (Exception e) {
            Assert.fail("'Product' class was not found!");
        }
    }

    @Test
    public void productGettersWork() {
        SubProduct sb = new SubProduct("abcdef", "SubProduct", 199.99, 10);
        
        assertTrue(sb.getId().equals("abcdef"));
        assertTrue(sb.getName().equals("SubProduct"));
        assertTrue(199.99 == sb.getPrice());
        assertEquals(10, sb.getCount());
    }

    @Test
    public void productSettersWork() {
        SubProduct sb = new SubProduct("abcdef", "SubProduct", 199.99, 10);

        sb.setId("defabc");
        sb.setName("ProductSub");
        sb.setPrice(399.99);
        sb.setCount(1);

        assertTrue(sb.getId().equals("defabc"));
        assertTrue(sb.getName().equals("ProductSub"));
        assertTrue(sb.getPrice() == 399.99);
        assertEquals(1, sb.getCount());
    }

    @Test
    public void productEqualsMethodWorks() {
        SubProduct sb = new SubProduct("abcdef", "SubProduct", 199.99, 10);
        SubProduct other = new SubProduct("abcdef", "SubProduct", 199.99, 10);
        SubProduct another = new SubProduct("defghk", "SubProduct", 199.99, 11);

        assertEquals(sb, other);
        assertNotEquals(sb, another);
    }

    @Test
    public void productToStringMethodWorks() {
        SubProduct sb = new SubProduct("abcdef", "SubProduct", 199.99, 10);

        assertTrue(sb.toString().equals("SubProduct"));
    }
}

class SubProduct extends Product {
    public SubProduct(String id, String name, double price, int count) {
        super(id, name, price, count);
    }
}
