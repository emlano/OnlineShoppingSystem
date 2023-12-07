public interface ShoppingManager {
    void start();
    void addProduct(Product product);
    void addUser(User user);
    Product deleteProduct(String productId);
    void printProductList();
    void saveToFile();
    void readFromFile();
    void startGUI();
}
