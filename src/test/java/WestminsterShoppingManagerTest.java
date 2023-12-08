import enums.Access;
import lib.Product;
import lib.User;
import org.junit.*;
import org.junit.jupiter.api.DisplayName;

import exceptions.*;
import ui.WestminsterShoppingManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("Testing class WestminsterShopping")
public class WestminsterShoppingManagerTest {
    
    @Test
    @DisplayName("Products are able to be added")
    public void methodAddProductWorks() {
        SubProduct sb = new SubProduct("abcdef", "ABCD", 100.99, 10);
        WestminsterShoppingManager wsm = new WestminsterShoppingManager();
        
        try {
            wsm.addProduct(sb);
        } catch (NonUniqueProductIdException e) {
            e.printStackTrace();
        }

        assertEquals(new ArrayList<Product>(List.of(sb)), wsm.getProductList());
    }

    @Test
    @DisplayName("Products are able to be retrieved")
    public void getProductMethodWorks() {
        SubProduct sb = new SubProduct("abcdef", "ABCD", 100.99, 10);
        WestminsterShoppingManager wsm = new WestminsterShoppingManager();
        
        try {
            wsm.addProduct(sb);
        } catch (NonUniqueProductIdException e) {
            e.printStackTrace();
        }

        assertEquals(sb, wsm.getProduct(sb.getId()));
    }

    @Test
    @DisplayName("Rejects products with non unique Ids")
    public void productIdCheckedForUniqueness() {
        SubProduct sb = new SubProduct("abcdef", "ABCD", 100, 1);
        SubProduct sbClone = new SubProduct("abcdef", "ZNBC", 99, 1);
        WestminsterShoppingManager wsm = new WestminsterShoppingManager();

        try {
            wsm.addProduct(sb);
        } catch (NonUniqueProductIdException e) {
            e.printStackTrace();
        }

        assertThrows(NonUniqueProductIdException.class, () -> wsm.addProduct(sbClone));
    }

    @Test
    @DisplayName("Users are able to be added")
    public void addUserMethodWorks() {
        SubUser su = new SubUser("lib.User", "Password", Access.ADMIN);
        WestminsterShoppingManager wsm = new WestminsterShoppingManager();

        try {
            wsm.addUser(su);
        } catch (NonUniqueUsernameException e) {
            e.printStackTrace();
        }

        assertEquals(new ArrayList<User>(List.of(su)), wsm.getUserList());
    }

    @Test
    @DisplayName("Users are able to be retrieved")
    public void getUserMethodWorks() {
        SubUser su = new SubUser("lib.User", "pass", Access.ADMIN);
        WestminsterShoppingManager wsm = new WestminsterShoppingManager();

        try {
            wsm.addUser(su);
        } catch (NonUniqueUsernameException e) {
            e.printStackTrace();
        }

        assertEquals(su, wsm.getUser(su.getUsername()));
    }

    @Test
    @DisplayName("Rejects new user account with non unique usernames")
    public void checksUsernameForUniqueness() {
        SubUser su = new SubUser("user1", "pass", Access.ADMIN);
        SubUser su2 = new SubUser("user1", "pass", Access.ADMIN);
        WestminsterShoppingManager wsm = new WestminsterShoppingManager();

        try {
            wsm.addUser(su);
        } catch (NonUniqueUsernameException e) {
            e.printStackTrace();
        }

        assertThrows(NonUniqueUsernameException.class, () -> wsm.addUser(su2));
    }
}
