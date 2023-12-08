import exceptions.NonUniqueProductIdException;
import exceptions.NonUniqueUsernameException;
import ui.UserInterface;
import ui.WestminsterShoppingManager;
import lib.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.start();
    }
}
