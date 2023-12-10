package ui;

import exceptions.*;
import lib.*;

import java.io.IOException;

public interface ShoppingManager {
    void addProduct(Product product) throws NonUniqueProductIdException;
    void addUser(User user) throws NonUniqueUsernameException;
    Product deleteProduct(String productId) throws ProductNotFoundException;
    void printProductList();
    void saveToFile(String file) throws IOException;
    void readFromFile(String file) throws IOException, CorruptedFileDataException;
    void startGUI();
}
