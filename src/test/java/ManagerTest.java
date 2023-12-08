import enums.Access;
import lib.Manager;
import lib.User;
import org.junit.*;
import org.junit.jupiter.api.DisplayName;
import static org.junit.Assert.*;

@DisplayName("Testing lib.Manager class")
public class ManagerTest {
    
    @Test
    @DisplayName("lib.Manager class must exist")
    public void managerClassExists() {
        try {
            Class.forName("lib.Manager");
        } catch (Exception e) {
            Assert.fail("Class 'lib.Manager' was not found!");
        }
    }

    @Test
    @DisplayName("lib.Manager class inherits from lib.User class")
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
