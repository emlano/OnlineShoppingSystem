import enums.Access;
import lib.User;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing class User")
public class UserTest {
    
    @Test
    @DisplayName("Abstract class user must exist")
    public void userClassExists() {
        try {
            Class.forName("lib.User");
        } catch (Exception e) {
            Assertions.fail("Abstract class 'User' not found!");
        }
    }

    @Test
    @DisplayName("Testing User's Getters")
    public void userGettersWork() {
        SubUser su = new SubUser("admin", "password", Access.ADMIN);

        assertEquals("admin", su.getUsername());
        assertEquals("password", su.getPassword());
        assertEquals(Access.ADMIN, su.getAccess());
    }

    @Test
    @DisplayName("Testing User's Setters")
    public void userSettersWork() {
        SubUser su = new SubUser("admin", "password", Access.ADMIN);

        su.setUsername("Mike");
        su.setPassword("Password1234");
        su.setAccess(Access.CLIENT);

        assertEquals("Mike", su.getUsername());
        assertEquals("Password1234", su.getPassword());
        assertEquals(Access.CLIENT, su.getAccess());
    }
}

class SubUser extends User {
    public SubUser(String username, String password, Access access) {
        super(username, password, access);
    }
}
