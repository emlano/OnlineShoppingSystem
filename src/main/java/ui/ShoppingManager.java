package ui;

import exceptions.*;
import lib.*;

public interface ShoppingManager {
    void start();
    void addProduct(Product product) throws NonUniqueProductIdException;
    void addUser(User user) throws NonUniqueUsernameException;
    Product deleteProduct(String productId);
    void printProductList();
    void saveToFile();
    void readFromFile();
    void startGUI();
}
