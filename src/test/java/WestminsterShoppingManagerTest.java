import enums.Access;
import lib.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.*;
import org.junit.jupiter.api.DisplayName;

import exceptions.*;
import ui.WestminsterShoppingManager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
            Assert.fail("Test Error: Product ID already used!");
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
            Assert.fail("Test Error: Product ID already used!");
        }

        assertEquals(sb, wsm.getProduct(sb.getId()));
    }

    @Test
    @DisplayName("Products can be deleted")
    public void deleteProductMethodWorks() {
        SubProduct sb = new SubProduct("ABCDE", "ABCD", 100, 1);
        WestminsterShoppingManager wsm = new WestminsterShoppingManager();
        try {
            wsm.addProduct(sb);
        } catch (NonUniqueProductIdException e) {
            Assert.fail("Test Error: Product ID already used!");
        }

        try {
            wsm.deleteProduct("ABCDE");
        } catch (ProductNotFoundException e) {
            Assert.fail("Test Failed: Product was not found!");
        }

        assertNull(wsm.getProduct("ABCDE"));
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
            Assert.fail("Test Error: Product ID already used!");
        }

        assertThrows(NonUniqueProductIdException.class, () -> wsm.addProduct(sbClone));
    }

    @Test
    @DisplayName("Users are able to be added")
    public void addUserMethodWorks() {
        SubUser su = new SubUser("User", "Password", Access.ADMIN);
        WestminsterShoppingManager wsm = new WestminsterShoppingManager();

        try {
            wsm.addUser(su);
        } catch (NonUniqueUsernameException e) {
            Assert.fail("Test Error: Username already used!");
        }

        assertEquals(new ArrayList<User>(List.of(su)), wsm.getUserList());
    }

    @Test
    @DisplayName("Users are able to be retrieved")
    public void getUserMethodWorks() {
        SubUser su = new SubUser("User", "pass", Access.ADMIN);
        WestminsterShoppingManager wsm = new WestminsterShoppingManager();

        try {
            wsm.addUser(su);
        } catch (NonUniqueUsernameException e) {
            Assert.fail("Test Error: Username already used!");
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
            Assert.fail("Test Error: Username already used!");
        }

        assertThrows(NonUniqueUsernameException.class, () -> wsm.addUser(su2));
    }

    @Test
    @DisplayName("Correctly sorts product list")
    public void sortProductListMethodWorks() {
        SubProduct sp = new SubProduct("AAAA", "AAAA", 100.00, 1);
        SubProduct sp1 = new SubProduct("AA", "AAAA", 100.00, 1);
        SubProduct sp2 = new SubProduct("AABC", "AAAA", 100.00, 1);
        SubProduct sp3 = new SubProduct("ABBB", "AAAA", 100.00, 1);
        SubProduct sp4 = new SubProduct("ABCD", "AAAA", 100.00, 1);

        ArrayList<Product> list = new ArrayList<>(List.of(sp, sp1, sp2, sp3, sp4));
        ArrayList<Product> sortedList = new ArrayList<>(List.of(sp1, sp, sp2, sp3, sp4));

        WestminsterShoppingManager wsm = new WestminsterShoppingManager();

        try {
            wsm.addProduct(sp);
            wsm.addProduct(sp1);
            wsm.addProduct(sp2);
            wsm.addProduct(sp3);
            wsm.addProduct(sp4);
        } catch (NonUniqueProductIdException e) {
            Assert.fail("Test Error: Product Id used twice!");
        }

        wsm.sortProductList(0);
        assertEquals(sortedList, wsm.getProductList());

        wsm.sortProductList(1);
        assertEquals(sortedList, wsm.getProductList());
    }

    @Test
    @DisplayName("Checking JSON parsers")
    public void jsonParserMethodsWork() {
        Clothing c = new Clothing("ABCDEF", "White button-up Shirt", 100.99, 1, "L", "White");
        Electronic e = new Electronic("ABCDEG", "Dell G5505 SE Laptop", 1499.99, 3, "Dell", 36);
        Client cl = new Client("harrypotter", "expelliarmus");
        Manager m = new Manager("gandalf", "mellon");
        cl.addToPurchaseHistory(c);
        cl.addToPurchaseHistory(e);

        ArrayList<Product> prodList = new ArrayList<>();
        ArrayList<User> userList = new ArrayList<>();
        prodList.add(c);
        prodList.add(e);

        userList.add(cl);
        userList.add(m);

        WestminsterShoppingManager wsm = new WestminsterShoppingManager();
        JSONArray prodArray = wsm.getJsonArrayFromProductList(prodList);
        JSONArray userArray = wsm.getJsonArrayFromUserList(userList);

        assertEquals(prodList, wsm.getProductListFromJsonArray(prodArray));
        assertEquals(userList, wsm.getUserListFromJsonArray(userArray));
    }

    @Test
    @DisplayName("Checking file readers/writers")
    public void fileReadingWritingMethodsWork() {
        Clothing c = new Clothing("ABCDEF", "White button-up Shirt", 100.99, 1, "L", "White");
        Electronic e = new Electronic("ABCDEG", "Dell G5505 SE Laptop", 1499.99, 3, "Dell", 36);
        Client cl = new Client("harrypotter", "expelliarmus");
        Manager m = new Manager("gandalf", "mellon");

        cl.addToPurchaseHistory(c);
        cl.addToPurchaseHistory(e);

        WestminsterShoppingManager wsm = new WestminsterShoppingManager();

        try {
            wsm.addUser(cl);
            wsm.addUser(m);
            wsm.addProduct(c);
            wsm.addProduct(e);
        } catch (NonUniqueUsernameException | NonUniqueProductIdException x) {
            Assert.fail("Test Failed: Duplicate Id Entries");
        }

        try {
            File temp = new File("temp.json");
            wsm.saveToFile("temp.json");
            wsm.readFromFile("temp.json");
            temp.delete();
        } catch (IOException x) {
            Assert.fail("Failed to open test file to write/read");
        }

        assertEquals(wsm.getProductList(), new ArrayList<Product>(List.of(c, e)));
        assertEquals(wsm.getUserList(), new ArrayList<User>(List.of(cl, m)));
    }
}
