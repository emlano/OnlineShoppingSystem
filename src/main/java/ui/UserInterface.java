package ui;

import exceptions.*;
import enums.*;
import lib.*;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class UserInterface {
    private final Scanner scn;
    private final WestminsterShoppingManager wsm;
    private final Random random;
    private static final String err = "\n[ ! ] An error occurred: ";
    private static User currentUser;

    public UserInterface() {
        this.scn = new Scanner(System.in);
        this.wsm = new WestminsterShoppingManager();
        this.random = new Random();
    }

    public void start() {
        System.out.println("\n\t> Attempting auto reload of program data...");
        try {
            wsm.readFromFile("data.json");
            System.out.println("\t> Load successful!");
        } catch (IOException e) {
            System.out.println("\t> No save file present.");
        } catch (CorruptedFileDataException e) {
            System.out.println(err + e.getMessage());
        }

        System.out.println();
        userMenu();
    }

    public void adminMenu() {
        showAdminMenu();

        while (true) {
            System.out.print("\nEnter an option: ");
            String option = scn.nextLine().trim();

            switch (option) {
                case "1" -> addProduct();
                case "2" -> deleteProduct();
                case "3" -> printProductList();
                case "4" -> saveData();
                case "5" -> reloadData();
                case "6" -> showAdminMenu();
                case "7" -> logout();
                case "8" -> destroyState();
                case "9" -> System.exit(0);
                default -> System.out.println("\nPlease choose a valid option!");
            }
        }
    }

    public void clientMenu() {
        showClientMenu();

        while (true) {
            System.out.print("\nEnter an option: ");
            String option = scn.nextLine().trim();

            switch (option) {
                case "1" -> wsm.startGUI(); //TODO
                case "2" -> logout();
                case "3" -> saveData();
                case "4" -> System.exit(0);
            }
        }
    }

    private void userMenu() {
        System.out.println("""
                       
                       Welcome!
                       --------
                       
                Online Shopping Manager
                    ---------------
                    
                1. Sign in
                2. Sign up
                3. Show user list
                4. Quit""");

        while (currentUser == null) {

            System.out.print("\nChoose an option: ");
            String option = scn.nextLine().trim();

            switch (option) {
                case "1" -> signIn();
                case "2" -> signUp();
                case "3" -> showUsers();
                case "4" -> System.exit(0);
                default -> System.out.println("\nPlease choose a valid option!");
            }
        }
    }

    private void showAdminMenu() {
        System.out.println("""
        
        Online Shopping System -- Administrator Menu
        ____________________________________________
            1. Add a new product.
            2. Delete a product.
            3. Print list of products.
            4. Save data in a file.
            5. Reload session from file.
            6. Show this menu.
            7. Logout.
            8. DESTROY DATA.
            9. Quit.""");
    }

    private void showClientMenu() {
        System.out.println("""
                
        Online Shopping System -- Client Menu
        _____________________________________
            1. Launch GUI.
            2. Logout.
            3. Quit""");
    }


    private void signIn() {
        try {
            String username = InputValidator.getStringInput("\n\tEnter username: ", InputFlag.NONE);
            String password = InputValidator.getStringInput("\tEnter password: ", InputFlag.NONE);

            User user = wsm.getUser(username);

            if (!user.getPassword().equals(password)) throw new IncorrectPasswordException();

            currentUser = user;
            System.out.println("\nSign in successful!");

            if (user.getAccess() == Access.ADMIN) adminMenu();
            else clientMenu();

        } catch (IllegalInputException | UserNotFoundException | IncorrectPasswordException e) {
            System.out.println(err + e.getMessage());
        }
    }

    private void signUp() {
        System.out.println("\n\t\t1. Administrator\n\t\t2. Client\n");
        try {
            int type = InputValidator.getIntInput("\tEnter account type: ", InputFlag.NONE, 1, 2);

            String username = InputValidator.getStringInput("\tEnter username: ", InputFlag.NONE, 1, 15);
            String password = InputValidator.getStringInput("\tEnter password: ", InputFlag.NONE, 1, 15);

            User user = switch (type) {
                case 1 -> new Manager(username, password);
                case 2 -> new Client(username, password);
                default -> throw new RuntimeException("No such account type found!");
            };

            wsm.addUser(user);
            currentUser = user;
            System.out.println("\nSign up successful!");

            if (type == 1) adminMenu();
            else clientMenu();

        } catch (IllegalInputException | OutOfBoundsException | NonUniqueUsernameException | RuntimeException e) {
            System.out.println(err + e.getMessage());
        }
    }

    private void logout() {
        currentUser = null;
        userMenu();
    }

    private void showUsers() {
        System.out.printf("\n\t%15.15s | %15.15s%n", "User", "Access");
        System.out.printf("\t%15.15s | %15.15s%n", "-".repeat(15), "-".repeat(15));

        wsm.getUserList().forEach(e -> System.out.printf("\t%15.15s | %15.15s%n", e.getUsername(), e.getAccess()));

        System.out.println();
    }

    private void addProduct() {
        try {
            System.out.println();

            String id = InputValidator.getStringInput(
                    "\tEnter product Id (Leave black to Generate an ID) : ",
                    InputFlag.CANBEBLANK, 8, 10
            );

            if (id.isBlank()) id = createProductId();

            String name = InputValidator.getStringInput("\tEnter product name: ", InputFlag.NONE,1, 15);

            int count = InputValidator.getIntInput("\tEnter an item count to add: ", InputFlag.CANBEBLANK);
            double price = InputValidator.getDoubleInput("\tEnter an price for the item: ", InputFlag.NONE);

            System.out.println("\n\t\t1. Clothing\n\t\t2. Electronic\n");
            int type = InputValidator.getIntInput("\tEnter an option: ", InputFlag.NONE, 1, 2);

            Product obj;
            if (type == 1) {
                Size size = InputValidator.getSizeInput("\tEnter items Size: ", InputFlag.NONE);
                String color = InputValidator.getStringInput("\tEnter items color : ", InputFlag.NONE,1, 15);
                obj = new Clothing(id, name, price, count, size, color);

            } else {
                String brand = InputValidator.getStringInput("\tEnter brand of item: ", InputFlag.NONE,1, 15);
                int warranty = InputValidator.getIntInput("\tEnter warranty of item in months: ", InputFlag.NONE);
                obj = new Electronic(id, name, price, count, brand, warranty);
            }

            wsm.addProduct(obj);

        } catch (IllegalInputException | OutOfBoundsException | NonUniqueProductIdException e) {
            System.out.println(err + e.getMessage());
        }
    }

    private void deleteProduct() {
        try {
            System.out.println();
            String id = InputValidator.getStringInput("\tEnter product id of the item: ", InputFlag.NONE,8, 10);
            wsm.deleteProduct(id);
            System.out.println("\nItem successfully deleted!");

        } catch (IllegalInputException | ProductNotFoundException e) {
            System.out.println(err + e.getMessage());
        }
    }

    private void printProductList() {
        System.out.println("\nPrinting all products...\n");
        wsm.printProductList();
    }

    private void saveData() {
        try {
            wsm.saveToFile("data.json");
            System.out.println("\nSaved data to 'data.json' file");
        } catch (IOException e) {
            System.out.println(err + "Could not open file to write!");
        }
    }

    private void reloadData() {
        try {
            wsm.readFromFile("data.json");
            System.out.println("\nLoaded data from 'data.json' file");

        } catch (IOException | CorruptedFileDataException e) {
            System.out.println(err + "Problem reading file, " + e.getMessage());
        }
    }

    private void destroyState() {
        System.out.println("\nProgram will DELETE ALL DATA!");
        System.out.println("Save file will not be cleared.");

        System.out.print("\tIf you are certain, type 'YES' and hit enter to continue... ");
        String confirmation = scn.nextLine().trim();

        if (confirmation.equals("YES")) {
            wsm.destroyState();
            System.out.println("\nProgram cleared of data...\n");
        }
    }

    private String createProductId() {
        final int LEFTBOUND = 48;
        final int RIGHTBOUND = 123;
        final int LENGTH = 10;

        StringBuilder str = new StringBuilder();

        int count = 0;
        while (count < LENGTH) {
            int i = random.nextInt(LEFTBOUND, RIGHTBOUND);

            if ((i >= 48 && i <= 57) || (i >= 65 && i <= 90)) {
                count++;
                str.append((char) i);
            }
        }

        System.out.println("\tGenerated ID: " + str.toString().toUpperCase());
        return str.toString().toUpperCase();
    }

}
