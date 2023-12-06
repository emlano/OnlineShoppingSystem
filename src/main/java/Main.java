public class Main {
    public static void main(String[] args) {
        WestminsterShoppingManager wb = new WestminsterShoppingManager();

        Clothing shirt = new Clothing("A2G5R4e8ubg", "Black Jeans Hardhat Basin Pelicope", 10);
        Clothing shirt2 = new Clothing("2345", "White Jeans", 10);
        Clothing shirt3 = new Clothing("3456", "Blue Jeans", 10);
        Clothing shirt4 = new Clothing("4567", "Billy Jeans", 10);
        Electronic remote = new Electronic("Misubishi Toyota", 9);

        Manager u1 = new Manager("rosebud", "pass");
        Manager u2 = new Manager("cornelius", "pass");
        Client u3 = new Client("karkoff", "pass");

        Electronic e1 = new Electronic("Samsung", 2);
        Electronic e2 = new Electronic("Singer", 3);
        Electronic e3 = new Electronic("Toyota", 10);

        u3.addToPurchaseHistory(e1);
        u3.addToPurchaseHistory(e2);
        u3.addToPurchaseHistory(e3);

        wb.addProduct(shirt);
        wb.addProduct(shirt2);
        wb.addProduct(shirt3);
        wb.addProduct(shirt4);
        wb.addProduct(remote);
        wb.addProduct(remote);
        wb.addProduct(remote);
        wb.addProduct(remote);
        wb.addUser(u1);
        wb.addUser(u2);
        wb.addUser(u3);

        wb.saveToFile();
    }
}
