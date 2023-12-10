import enums.Access;
import lib.Manager;
import lib.User;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing Manager class")
public class ManagerTest {
    
    @Test
    @DisplayName("Manager class must exist")
    public void managerClassExists() {
        try {
            Class.forName("lib.Manager");
        } catch (Exception e) {
            Assertions.fail("Class 'Manager' was not found!");
        }
    }

    @Test
    @DisplayName("Manager class inherits from User class")
    public void managerInheritsFromUser() {
        assertEquals(User.class, Manager.class.getSuperclass());
    }

    @Test
    @DisplayName("Verify manager object access")
    public void managerIsAdmin() {
        Manager mng = new Manager("John", "password");

        assertEquals(Access.ADMIN, mng.getAccess());
    }
}
