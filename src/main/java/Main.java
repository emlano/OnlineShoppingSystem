import exceptions.NonUniqueProductIdException;
import exceptions.NonUniqueUsernameException;
import ui.WestminsterShoppingManager;
import lib.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        WestminsterShoppingManager wb = new WestminsterShoppingManager();

         Clothing c1 = new Clothing("A1B4U3I98", "Cowboy Hat", 544.99, 10, "L", "Brown");
         Clothing c2 = new Clothing("Z1E45A4L8", "Polo Shirt", 34.55, 5, "XXL", "White");
         Clothing c3 = new Clothing("P13SFF078", "Blue Jeans", 199.00, 8, "S", "Blue");
         Clothing c4 = new Clothing("I13F5B5VC", "Yellow Hoodie", 83.95, 3, "XL", "Yellow");
         Clothing c5 = new Clothing("H7C4H99NS", "Cargo Pants", 199.95, 1, "XS", "Black");

         Electronic e1 = new Electronic("H7GSD8J2S", "Television", 1000.00, 10, "Toshiba", 2);
         Electronic e2 = new Electronic("J8H7F3GG3", "Car Radio", 155.99, 5, "Kenwood", 1);
         Electronic e3 = new Electronic("H7A8SB213", "Laptop", 900.99, 5, "Lenovo", 3);
         Electronic e4 = new Electronic("V92NAJSDA", "Washing Machine", 599.99, 2, "Panasonic", 3);
         Electronic e5 = new Electronic("BA92J12Q2", "Smartphone", 1499.99, 20, "Samsung", 1);

         Manager m1 = new Manager("rosebud", "********");
         Manager m2 = new Manager("harrypotter", "leviOsaa");
         Manager m3 = new Manager("isengard", "takingthehobbitsto");
         Manager m4 = new Manager("secondson", "anyafolger");
         Manager m5 = new Manager("voldymorte", "avadakedvra");

         Client cl1 = new Client("karlmax", "theCommuneManifesto");
         Client cl2 = new Client("vaatividya", "prepare2cry");
         Client cl3 = new Client("godrickdagolden", "icmdtheeKNEEL");
         Client cl4 = new Client("morgot_alt_acc", "foolishambitions");
         Client cl5 = new Client("therealmorgot", "writ_it_upon_thy_meagre_grave");

         cl1.addToPurchaseHistory(c1);
         cl1.addToPurchaseHistory(e1);
         cl2.addToPurchaseHistory(c2);
         cl2.addToPurchaseHistory(e2);
         cl3.addToPurchaseHistory(c3);
         cl3.addToPurchaseHistory(e3);
         cl4.addToPurchaseHistory(c4);
         cl4.addToPurchaseHistory(e4);
         cl5.addToPurchaseHistory(c5);
         cl5.addToPurchaseHistory(e5);

         try {
             wb.addProduct(c1);
             wb.addProduct(c2);
             wb.addProduct(c3);
             wb.addProduct(c4);
             wb.addProduct(c5);
             wb.addProduct(e1);
             wb.addProduct(e2);
             wb.addProduct(e3);
             wb.addProduct(e4);
             wb.addProduct(e5);
         } catch (NonUniqueProductIdException e) {
             e.printStackTrace();
         }

         try {
             wb.addUser(m1);
             wb.addUser(m2);
             wb.addUser(m3);
             wb.addUser(m4);
             wb.addUser(m5);

             wb.addUser(cl1);
             wb.addUser(cl2);
             wb.addUser(cl3);
             wb.addUser(cl4);
             wb.addUser(cl5);

             wb.saveToFile("data.json");
             wb.readFromFile("data.json");
             wb.printProductList();
         } catch (NonUniqueUsernameException | IOException e) {
             e.printStackTrace();
         }


    }
}
