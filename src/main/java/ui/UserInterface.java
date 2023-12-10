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

    public UserInterface() {
        this.scn = new Scanner(System.in);
        this.wsm = new WestminsterShoppingManager();
        this.random = new Random();
    }

    public void start() {
        showMenu();

        while (true) {
            System.out.print("\nEnter an option: ");
            String option = scn.nextLine().trim();

            switch (option) {
                case "1" -> addProduct();
                case "2" -> deleteProduct();
                case "3" -> printProductList();
                case "4" -> saveData();
                case "5" -> reloadData();
                case "6" -> showMenu();
                case "7" -> System.exit(0);
                default -> {}
            }
        }
    }

    private void showMenu() {
        System.out.println("""
        
        Online Shopping System -- Administrator Menu
        ____________________________________________
            1. Add a new product.
            2. Delete a product.
            3. Print list of products.
            4. Save data in a file.
            5. Reload session from file.
            6. Show this menu.
            7. Quit.""");
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
