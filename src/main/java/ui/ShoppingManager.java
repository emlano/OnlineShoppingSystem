package ui;

import exceptions.CorruptedFileDataException;
import exceptions.NonUniqueProductIdException;
import exceptions.NonUniqueUsernameException;
import exceptions.ProductNotFoundException;
import lib.Product;
import lib.User;

import java.io.IOException;

public interface ShoppingManager {
    void addProduct(Product product) throws NonUniqueProductIdException;
    void addUser(User user) throws NonUniqueUsernameException;
    Product deleteProduct(String productId) throws ProductNotFoundException;
    void printProductList();
    void saveToFile(String file) throws IOException;
    void readFromFile(String file) throws IOException, CorruptedFileDataException;
    void startGUI(User user);
}
