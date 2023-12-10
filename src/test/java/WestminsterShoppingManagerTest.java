import enums.*;
import lib.*;
import org.json.*;
import org.junit.jupiter.api.*;

import exceptions.*;
import ui.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing class WestminsterShopping")
public class WestminsterShoppingManagerTest {

    @Test
    @DisplayName("Possible to add products")
    public void methodAddProductWorks() {
        SubProduct sb = new SubProduct("abcdef", "ABCD", 100.99, 10);
        WestminsterShoppingManager wsm = new WestminsterShoppingManager();
        
        try {
            wsm.addProduct(sb);
        } catch (NonUniqueProductIdException e) {
            Assertions.fail("Test Error: Product ID already used!");
        }

        assertNotNull( wsm.getProductList(), "Product list was null");
        assertEquals(1, wsm.getProductList().size(), "List length was wrong");
        assertEquals(new ArrayList<Product>(List.of(sb)), wsm.getProductList(), "List content was wrong");
    }

    @Test
    @DisplayName("Possible to return products")
    public void getProductMethodWorks() {
        SubProduct sb = new SubProduct("abcdef", "ABCD", 100.99, 10);
        WestminsterShoppingManager wsm = new WestminsterShoppingManager();
        
        try {
            wsm.addProduct(sb);
        } catch (NonUniqueProductIdException e) {
            Assertions.fail("Test Error: Product ID already used!");
        }

        assertThrows(ProductNotFoundException.class, () -> wsm.getProduct("invalidId"), "Non-existent product search didn't throw");

        try {
            assertNotNull(wsm.getProduct(sb.getId()), "Failed to find product");
            assertEquals(sb, wsm.getProduct(sb.getId()), "Returned object was wrong");
        } catch (ProductNotFoundException e) {
            Assertions.fail("Added products were not found");
        }
    }

    @Test
    @DisplayName("Products can be deleted")
    public void deleteProductMethodWorks() {
        SubProduct sb = new SubProduct("ABCDE", "ABCD", 100, 1);
        WestminsterShoppingManager wsm = new WestminsterShoppingManager();
        try {
            wsm.addProduct(sb);
        } catch (NonUniqueProductIdException e) {
            Assertions.fail("Test Error: Product ID already used!");
        }

        try {
            wsm.deleteProduct("ABCDE");
        } catch (ProductNotFoundException e) {
            Assertions.fail("Test Failed: Product was not found!");
        }

        assertThrows(ProductNotFoundException.class, () -> wsm.deleteProduct("invalidId"), "Did not throw for a invalid deletion");
        assertThrows(ProductNotFoundException.class, () -> wsm.getProduct(sb.getId()));
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
            Assertions.fail("Test Error: Product ID already used!");
        }

        assertThrows(NonUniqueProductIdException.class, () -> wsm.addProduct(sbClone), "Failed to detect duplicate Id");
    }

    @Test
    @DisplayName("Users are able to be added")
    public void addUserMethodWorks() {
        SubUser su = new SubUser("User", "Password", Access.ADMIN);
        WestminsterShoppingManager wsm = new WestminsterShoppingManager();

        try {
            wsm.addUser(su);
        } catch (NonUniqueUsernameException e) {
            Assertions.fail("Test Error: Username already used!");
        }

        assertNotNull(wsm.getUserList(), "User list was null");
        assertEquals(1, wsm.getUserList().size(), "Returned list's size was wrong");
        assertEquals(new ArrayList<User>(List.of(su)), wsm.getUserList(), "Returned list was wrong");
    }

    @Test
    @DisplayName("Users are able to be retrieved")
    public void getUserMethodWorks() {
        SubUser su = new SubUser("User", "pass", Access.ADMIN);
        WestminsterShoppingManager wsm = new WestminsterShoppingManager();

        try {
            wsm.addUser(su);
        } catch (NonUniqueUsernameException e) {
            Assertions.fail("Test Error: Username already used!");
        }

        assertThrows(UserNotFoundException.class, () -> wsm.getUser("invalidUsername"));

        try {
            assertNotNull(wsm.getUser(su.getUsername()), "Search failed to find user");
            assertEquals(su, wsm.getUser(su.getUsername()), "Wrong user returned");
        } catch (UserNotFoundException e) {
            Assertions.fail("Failed to find existing user");
        }
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
            Assertions.fail("Test Error: Username already used!");
        }

        assertThrows(NonUniqueUsernameException.class, () -> wsm.addUser(su2), "Accepted user with duplicate username");
    }

    @Test
    @DisplayName("Correctly sorts product list")
    public void sortProductListMethodWorks() {
        SubProduct sp = new SubProduct("AAAA", "AAAA", 100.00, 1);
        SubProduct sp1 = new SubProduct("AA", "AAAA", 100.00, 1);
        SubProduct sp2 = new SubProduct("AABC", "AAAA", 100.00, 1);
        SubProduct sp3 = new SubProduct("ABBB", "AAAA", 100.00, 1);
        SubProduct sp4 = new SubProduct("ABCD", "AAAA", 100.00, 1);

        ArrayList<Product> sortedList = new ArrayList<>(List.of(sp1, sp, sp2, sp3, sp4));

        WestminsterShoppingManager wsm = new WestminsterShoppingManager();

        try {
            wsm.addProduct(sp);
            wsm.addProduct(sp1);
            wsm.addProduct(sp2);
            wsm.addProduct(sp3);
            wsm.addProduct(sp4);
        } catch (NonUniqueProductIdException e) {
            Assertions.fail("Test Error: Product Id used twice!");
        }

        wsm.sortProductList(0);
        assertEquals(sortedList, wsm.getProductList(), "Order by ID failed");

        wsm.sortProductList(1);
        assertEquals(sortedList, wsm.getProductList(), "Order by name failed");
    }

    @Test
    @DisplayName("Checking JSON parsers")
    public void jsonParserMethodsWork() {
        Clothing c = new Clothing("ABCDEF", "White button-up Shirt", 100.99, 1, Size.L, "White");
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

        assertEquals(prodList, wsm.getProductListFromJsonArray(prodArray), "Failed to rebuild products from JSON");
        assertEquals(userList, wsm.getUserListFromJsonArray(userArray), "Failed to rebuild users from JSON");
    }

    @Test
    @DisplayName("Checking file readers/writers")
    public void fileReadingWritingMethodsWork() {
        Clothing c = new Clothing("ABCDEF", "White button-up Shirt", 100.99, 1, Size.L, "White");
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
            Assertions.fail("Test Failed: Duplicate Id Entries");
        }

        try {
            File temp = new File("temp.json");
            wsm.saveToFile("temp.json");
            wsm.readFromFile("temp.json");
            assertTrue(temp.delete());
        } catch (IOException | CorruptedFileDataException x) {
            Assertions.fail("Failed to open test file to write/read");
        }

        try {
            assertEquals(c, wsm.getProduct(c.getId()));
            assertEquals(e, wsm.getProduct(e.getId()));
            assertEquals(cl, wsm.getUser(cl.getUsername()));
            assertEquals(m, wsm.getUser(m.getUsername()));
        } catch (ProductNotFoundException | UserNotFoundException ex) {
            Assertions.fail("Test Failed: Wasn't able to find added users");
        }
    }

    @Test
    @DisplayName("Errors when the file is not available to be read")
    public void checksIfFileIsMissing() {
        WestminsterShoppingManager wsm = new WestminsterShoppingManager();

        assertThrows(IOException.class, () -> wsm.readFromFile("file.json"), "Did not throw exception when file was missing");
    }

    @Test
    @DisplayName("Handles when file is corrupted")
    public void checksIfFileIsCorrupted() {
        WestminsterShoppingManager wsm = new WestminsterShoppingManager();

        File temp = new File("temp.json");
        File temp2 = new File("temp2.json");
        File temp3 = new File("temp3.json");

        try {
            FileWriter fw = new FileWriter(temp);
            fw.write("TotallyNotJsonStrings");
            fw.close();

            FileWriter fw2 = new FileWriter(temp2);
            fw2.write("""
            {
                "users": [
                    {"invalid":  "json"}
                ],
                "products": [
                    {"veryInvalid": "json"}
                ]
            }
            """);
            fw2.close();

            FileWriter fw3 = new FileWriter(temp3);
            fw3.write("""
                    {
                        "users": [
                            {
                                "id":  1337,
                                "name": true,
                                "price": "hello",
                                "count": 1337
                            }
                        ],
                        "products": [
                            {"veryInvalid": "json"}
                        ]
                    }
                    """);
            fw3.close();

            assertThrows(CorruptedFileDataException.class, () -> wsm.readFromFile("temp.json"), "Did not throw when data was not JSON");
            assertThrows(CorruptedFileDataException.class, () -> wsm.readFromFile("temp2.json"), "Did not throw when json queries were wrong");
            assertThrows(CorruptedFileDataException.class, () -> wsm.readFromFile("temp3.json"), "Did not throw when data value types were wrong");

            assertTrue(temp.delete(), "Failed to delete temporary file");
            assertTrue(temp2.delete(), "Failed to delete temporary file");
            assertTrue(temp3.delete(), "Failed to delete temporary file");
        } catch (IOException e) {
            Assertions.fail("Test Failed: Unable to open file to write");
        }


    }
}
