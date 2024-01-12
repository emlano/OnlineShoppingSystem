package ui.gui;

import exceptions.NotEnoughProductStockException;
import lib.Client;
import lib.Clothing;
import lib.Electronic;
import lib.Product;
import lib.ShoppingCart;
import ui.gui.WComponents.WTable;
import ui.gui.models.ItemTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

// Contains all logic relevant to the GUI part
public class GraphicalLogic {
        // Used to check how to filter the products table
        // None filters nothing (All items displayed)
        public static final int NONE = 0;
        public static final int CLOTHING = 1;
        public static final int ELECTRONIC = 2;


    public static void setLabelFixedSize(JLabel label, int width, int height) {
        label.setMinimumSize(new Dimension(width, height));
        label.setPreferredSize(new Dimension(width, height));
        label.setMaximumSize(new Dimension(width, height));
    }

    public static void setLabelFixedSize(JLabel[] labelArr, int width, int height) {
        for (JLabel i : labelArr) {
            setLabelFixedSize(i, width, height);
        }
    }

    // Spawns windows from center of the screen
    public static void setFrameToCenter(int x, int y, JFrame frame) {
        frame.setLocation((x - frame.getWidth() / 2), (y - frame.getHeight() / 2));
    }

    // Used to set the info about the selected product from the table
    public static void setSelectedProdDescLabels(Product selectedProd, JLabel[] textLabelArr, JLabel[] dataLabelArr) {
        String[] headers = {"Product ID", "Category", "Name", null, null, "Items Available"};
        String[] data = {selectedProd.getId(), selectedProd.getClass().getSimpleName(), selectedProd.getName(), null, null, String.valueOf(selectedProd.getCount())};

        if (selectedProd instanceof Clothing c) {
            headers[3] = "Size";
            data[3] = c.getSize().toString();

            headers[4] = "Color";
            data[4] = c.getColor();
        }

        else {
            Electronic e = (Electronic) selectedProd;

            headers[3] = "Brand";
            data[3] = e.getBrand();

            headers[4] = "Warranty in Weeks";
            data[4] = String.valueOf(e.getWarrantyPeriod());
        }

        for (int i = 0; i < headers.length; i++) {
            textLabelArr[i].setText(headers[i]);
            dataLabelArr[i].setText(data[i]);
        }
    }

    // Refreshes table with filters optionally
    public static void redrawTable(int filter, ArrayList<Product> prodList, WTable table) {
        ArrayList<Product> displayedList;

        if (filter == NONE) {
            displayedList = prodList;
        } else {
            String filterStr = switch(filter) {
                case CLOTHING -> "Clothing";
                case ELECTRONIC -> "Electronic";
                default -> throw new IllegalArgumentException("Invalid argument for parameter 'filter' : " + filter);
            };

            displayedList = prodList
                    .stream()
                    .filter(e -> e.getClass().getSimpleName().equals(filterStr))
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        ItemTableModel tm = new ItemTableModel(displayedList);
        table.setModel(tm);
    }

    // Unsets the selected product details
    public static void setDefaultProdDescLabels(JLabel[] txtLabelArr, JLabel[] dataLabelArr) {
        final int LABEL_COUNT = txtLabelArr.length;

        for (int i = 0; i < LABEL_COUNT; i++) {
            if (txtLabelArr[i] == null) {
                txtLabelArr[i] = new JLabel("N/A");
                dataLabelArr[i] = new JLabel("N/A");
            
            } else {
                txtLabelArr[i].setText("N/A");
                dataLabelArr[i].setText("N/A");
            }
        }

        setLabelFixedSize(dataLabelArr, 120, 15);
        setLabelFixedSize(txtLabelArr, 120, 15);
    }

    public static void saveUserBuyHistory(Client client) {
        client.addToPurchaseHistory(client.getCart().getProductList());
        client.getCart().setProductList(new ArrayList<>());
    }

    public static void addProdToCart(Product selectedProd, ShoppingCart cart) {
        try {
            Product p = selectedProd.extract(1);
            cart.addToCart(p);

        } catch (CloneNotSupportedException e) {
            System.out.println("Warning! Internal error occurred! Couldn't clone Object!");

        } catch (NotEnoughProductStockException e) {
            JOptionPane.showMessageDialog(null, "Cannot add item to cart, Not enough Stock!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Returns unpurchased items back to the system
    public static void returnProdsFromCart(ArrayList<Product> prodList, ShoppingCart cart) {
        for (int i = 0; i < cart.getSize(); i++) {
            Product p = cart.getProduct(i);

            int prodListIndex = prodList.indexOf(p);

            if (prodListIndex == -1) return;
            
            Product prodFromList = prodList.get(prodListIndex);

            prodFromList.setCount(prodFromList.getCount() + p.getCount());
        }

        cart.emptyCart();
    }

    // Gets the item selected by user from the table
    public static Optional<Product> getSelectedProd(WTable table) {
        int selectedRow = table.getSelectedRow();
        return getProdFromRow(selectedRow, table);
    }

    public static Optional<Product> getProdFromRow(int row, WTable table) {
        ItemTableModel itm = (ItemTableModel) table.getModel();
        return itm.getProductAtRow(row);
    }
}
