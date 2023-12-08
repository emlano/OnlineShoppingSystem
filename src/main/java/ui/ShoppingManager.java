package ui;

import exceptions.NonUniqueProductIdException;
import exceptions.NonUniqueUsernameException;
import lib.Product;
import lib.User;

import java.io.IOException;

public interface ShoppingManager {
    void start();
    void addProduct(Product product) throws NonUniqueProductIdException;
    void addUser(User user) throws NonUniqueUsernameException;
    Product deleteProduct(String productId);
    void printProductList();
    void saveToFile(String file) throws IOException;
    void readFromFile(String file) throws IOException;
    void startGUI();
}
