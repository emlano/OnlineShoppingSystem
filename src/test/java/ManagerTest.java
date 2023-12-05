import org.junit.*;
import org.junit.jupiter.api.DisplayName;
import static org.junit.Assert.*;

@DisplayName("Testing Manager class")
public class ManagerTest {
    
    @Test
    @DisplayName("Manager class must exist")
    public void managerClassExists() {
        try {
            Class.forName("Manager");
        } catch (Exception e) {
            Assert.fail("Class 'Manager' was not found!");
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
