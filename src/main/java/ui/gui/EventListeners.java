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
    public static void armAddToCartButton(WTable table, Client client, JLabel[] prodTxtLabels, JLabel[] prodDataLabels) {
            Optional<Product> selectedProd = GraphicalLogic.getSelectedProd(table);

            if (selectedProd.isEmpty()) return;

            GraphicalLogic.addProdToCart(selectedProd.get(), client.getCart());
            GraphicalLogic.setSelectedProdDescLabels(selectedProd.get(), prodTxtLabels, prodDataLabels);
    }

    public static ListSelectionListener getTableListSelectionListener(WTable table, JLabel[] prodTxtLabels, JLabel[] prodDataLabels) {
        return e -> {
            Optional<Product> selectedProduct = GraphicalLogic.getSelectedProd(table);

            if (selectedProduct.isPresent()) GraphicalLogic.setSelectedProdDescLabels(selectedProduct.get(), prodTxtLabels, prodDataLabels);
            else GraphicalLogic.setDefaultProdDescLabels(prodTxtLabels, prodDataLabels);
        };
    }

    public static WindowListener getCartCloseListener(WTable table, JLabel[] prodTxtLabels, JLabel[] prodDataLabels, JFrame frame) {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
                
                Optional<Product> selectedProduct = GraphicalLogic.getSelectedProd(table);

                selectedProduct.ifPresent(product -> GraphicalLogic.setSelectedProdDescLabels(product, prodTxtLabels, prodDataLabels));

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

    public static void clearCart(JFrame mainFrame, ArrayList<Product> prodList, Client client, WTable table, JLabel[] txtLabelArr, JLabel[] dataLabelArr) {
        GraphicalLogic.returnProdsFromCart(prodList, client.getCart());
        Optional<Product> selectedProd = GraphicalLogic.getSelectedProd(table);

        selectedProd.ifPresent(prod -> GraphicalLogic.setSelectedProdDescLabels(prod, txtLabelArr, dataLabelArr));
        mainFrame.setVisible(true);
    }

    public static void checkout(Client client, JFrame mainFrame) {
        GraphicalLogic.saveUserBuyHistory(client);
        mainFrame.setVisible(true);
    }

    public static void killCartWindow(JFrame cartFrame, WTable table, JLabel[] prodTxtLabels, JLabel[] prodDataLabels) {
        cartFrame.dispose();
        Optional<Product> selectedProduct = GraphicalLogic.getSelectedProd(table);

        selectedProduct.ifPresent(product -> GraphicalLogic.setSelectedProdDescLabels(product, prodTxtLabels, prodDataLabels));
    }
}
