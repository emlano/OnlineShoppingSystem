import org.junit.*;
import org.junit.jupiter.api.DisplayName;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;

@DisplayName("Testing Client class")
public class ClientTest {
    @Test
    @DisplayName("Class client must exist")
    public void clientClassExists() {
        try {
            Class.forName("Client");
        } catch (Exception e) {
            Assert.fail("Class 'Client' not found!");
        }
    }

    @Test
    @DisplayName("Client class inherits from User")
    public void clientInheritsFromUser() {
        assertEquals(User.class, Client.class.getSuperclass());
    }

    @Test
    @DisplayName("Client class getters work")
    public void clientGettersWork() {
        Client cl = new Client("John", "password");

        assertEquals(new ArrayList<Product>(), cl.getPurchaseHistory());
    }

    @Test
    @DisplayName("Client purchase history works")
    public void clientPurchaseHistoryWorks() {
        Client cl = new Client("John", "password");
        Clothing shirt = new Clothing(Size.L, "Black");
        Clothing jeans = new Clothing(Size.L, "Blue");

        ArrayList<Product> list = new ArrayList<>(Arrays.asList(shirt, jeans));

        cl.addToPurchaseHistory(shirt);
        cl.addToPurchaseHistory(jeans);

        assertEquals(list, cl.getPurchaseHistory());
    }

    @Test
    @DisplayName("Client class setters work")
    public void clientSettersWork() {
        Client cl = new Client("John", "password");

        Clothing shirt = new Clothing(Size.L, "Black");
        Clothing jeans = new Clothing(Size.S, "Blue");

        ArrayList<Product> list = new ArrayList<>(Arrays.asList(shirt, jeans));

        assertEquals(new ArrayList<Product>(), cl.getPurchaseHistory());

        cl.setPurchaseHistory(list);

        assertEquals(list, cl.getPurchaseHistory());
    }
}
