package ui.gui;

import lib.Client;
import lib.Product;
import ui.gui.WComponents.WTable;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Optional;

public class EventListeners {
    public static void armAddToCartButton(WTable table, ArrayList<Product> cartList, JLabel[] prodTxtLabels, JLabel[] prodDataLabels) {
            Optional<Product> selectedProd = GraphicalLogic.getSelectedProd(table);

            GraphicalLogic.addProdToCart(selectedProd, cartList);
            GraphicalLogic.setSelectedProdDescLabels(selectedProd, prodTxtLabels, prodDataLabels);
    }

    public static ListSelectionListener getTableListSelectionListener(WTable table, JLabel[] prodTxtLables, JLabel[] prodDataLabels) {
        return e -> {
            Optional<Product> selectedProduct = GraphicalLogic.getSelectedProd(table);

            if (selectedProduct.isPresent()) GraphicalLogic.setSelectedProdDescLabels(selectedProduct, prodTxtLables, prodDataLabels);
            else GraphicalLogic.setDefaultProdDescLabels(prodTxtLables, prodDataLabels);
        };
    }

    public static WindowListener getCartCloseListener(WTable table, ArrayList<Product> prodList, ArrayList<Product> cartList, JLabel[] prodTxtLables, JLabel[] prodDataLabels, JFrame frame) {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
                
                Optional<Product> selectedProduct = GraphicalLogic.getSelectedProd(table);
                GraphicalLogic.returnProdsFromCart(prodList, cartList);
                GraphicalLogic.redrawTable(GraphicalLogic.NONE, prodList, table);
                GraphicalLogic.setSelectedProdDescLabels(selectedProduct, prodTxtLables, prodDataLabels);

                frame.setVisible(true);
            }
        };
    }

    public static ItemListener getComboBoxItemListener(ArrayList<Product> prodList, WTable table) {
        return e -> {
            int filter = switch(e.getItem().toString()) {
                case "Clothing" -> GraphicalLogic.CLOTHING;
                case "Electronic" -> GraphicalLogic.ELECTRONIC;
                case "All" -> GraphicalLogic.NONE;
                default -> throw new IllegalArgumentException("Product type selection box returned an invalid argument");
            };

            GraphicalLogic.redrawTable(filter, prodList, table);
        };
    }

    public static void clearCart(JFrame mainFrame, ArrayList<Product> prodList, ArrayList<Product> cartList) {
        GraphicalLogic.returnProdsFromCart(prodList, cartList);
        mainFrame.setVisible(true);
    }

    public static void checkout(Client client, JFrame mainFrame, ArrayList<Product> cartList) {
        GraphicalLogic.saveUserBuyHistory(cartList, client);
        mainFrame.setVisible(true);
    }

    public static void killCartWindow(JFrame cartFrame, WTable table, JLabel[] prodTxtLabels, JLabel[] prodDataLabels) {
        cartFrame.dispose();
        Optional<Product> selectedProduct = GraphicalLogic.getSelectedProd(table);
        GraphicalLogic.setSelectedProdDescLabels(selectedProduct, prodTxtLabels, prodDataLabels);
    }
}
