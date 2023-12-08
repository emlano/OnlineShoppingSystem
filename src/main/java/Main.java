import exceptions.NonUniqueProductIdException;
import exceptions.NonUniqueUsernameException;
import ui.WestminsterShoppingManager;

public class Main {
    public static void main(String[] args) {
        WestminsterShoppingManager wb = new WestminsterShoppingManager();

         lib.Clothing c1 = new lib.Clothing("A1B4U3I98", "Cowboy Hat", 544.99, 10, "L", "Brown");
         lib.Clothing c2 = new lib.Clothing("Z1E45A4L8", "Polo Shirt", 34.55, 5, "XXL", "White");
         lib.Clothing c3 = new lib.Clothing("P13SFF078", "Blue Jeans", 199.00, 8, "S", "Blue");
         lib.Clothing c4 = new lib.Clothing("I13F5B5VC", "Yellow Hoodie", 83.95, 3, "XL", "Yellow");
         lib.Clothing c5 = new lib.Clothing("H7C4H99NS", "Cargo Pants", 199.95, 1, "XS", "Black");

         lib.Electronic e1 = new lib.Electronic("H7GSD8J2S", "Television", 1000.00, 10, "Toshiba", 2);
         lib.Electronic e2 = new lib.Electronic("J8H7F3GG3", "Car Radio", 155.99, 5, "Kenwood", 1);
         lib.Electronic e3 = new lib.Electronic("H7A8SB213", "Laptop", 900.99, 5, "Lenovo", 3);
         lib.Electronic e4 = new lib.Electronic("V92NAJSDA", "Washing Machine", 599.99, 2, "Panasonic", 3);
         lib.Electronic e5 = new lib.Electronic("BA92J12Q2", "Smartphone", 1499.99, 20, "Samsung", 1);

         lib.Manager m1 = new lib.Manager("rosebud", "********");
         lib.Manager m2 = new lib.Manager("harrypotter", "leviOsaa");
         lib.Manager m3 = new lib.Manager("isengard", "takingthehobbitsto");
         lib.Manager m4 = new lib.Manager("secondson", "anyafolger");
         lib.Manager m5 = new lib.Manager("voldymorte", "avadakedvra");

         lib.Client cl1 = new lib.Client("karlmax", "theCommuneManifesto");
         lib.Client cl2 = new lib.Client("vaatividya", "prepare2cry");
         lib.Client cl3 = new lib.Client("godrickdagolden", "icmdtheeKNEEL");
         lib.Client cl4 = new lib.Client("morgot_alt_acc", "foolishambitions");
         lib.Client cl5 = new lib.Client("therealmorgot", "writ_it_upon_thy_meagre_grave");

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
         } catch (NonUniqueUsernameException e) {
             e.printStackTrace();
         }

         wb.saveToFile();
        wb.readFromFile();
        wb.printProductList();
    }
}
