import org.junit.*;

public class ProductTest {

    @Test
    public void productClassExists() {
        try {
            Class.forName("Product");
        } catch (Exception e) {
            Assert.fail("'Product' class was not found!");
        }
    }
}
