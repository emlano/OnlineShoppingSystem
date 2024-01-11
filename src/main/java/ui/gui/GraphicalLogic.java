package ui.gui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import exceptions.NotEnoughProductStockException;
import lib.Client;
import lib.Clothing;
import lib.Electronic;
import lib.Product;
import ui.gui.WComponents.WTable;
import ui.gui.models.ItemTableModel;

public class GraphicalLogic {
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

    public static void setFrameToCenter(int x, int y, JFrame frame) {
        frame.setLocation((x - frame.getWidth() / 2), (y - frame.getHeight() / 2));
    }

    public static void setSelectedProdDescLabels(Optional<Product> selectedProd, JLabel[] textLabelArr, JLabel[] dataLabelArr) {
        if (selectedProd.isEmpty()) return;
        Product p = selectedProd.get();

        String[] headers = {"Product ID", "Category", "Name", null, null, "Items Available"};
        String[] data = {p.getId(), p.getClass().getSimpleName(), p.getName(), null, null, String.valueOf(p.getCount())};

        if (p instanceof Clothing) {
            Clothing c = (Clothing) p;
            
            headers[3] = "Size";
            data[3] = c.getSize().toString();

            headers[4] = "Color";
            data[4] = c.getColor();
        }

        else {
            Electronic e = (Electronic) p;

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

    public static void redrawTable(int filter, ArrayList<Product> prodList, WTable table) {
        ArrayList<Product> displayedList = new ArrayList<>();

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

    public static void setDefaultProdDescLabels(JLabel[] txtLabelArr, JLabel[] dataLabelArr) {
        final int LABEL_COUNT = txtLabelArr.length;

        for (int i = 0; i < LABEL_COUNT; i++) {
            txtLabelArr[i] = new JLabel("N/A");
            dataLabelArr[i] = new JLabel("N/A");
        }

        setLabelFixedSize(dataLabelArr, 120, 15);
    }

    public static void saveUserBuyHistory(ArrayList<Product> cartList, Client client) {
        cartList.forEach(e -> client.addToPurchaseHistory(e));
        cartList.clear();
    }

    public static double calcTotal(ArrayList<Product> cartList) {
        return cartList.stream().mapToDouble(e -> e.getPrice()).sum();
    }

    public static double calcNewCustDisc(Client client, double total) {
        if (client.getPurchaseHistory().isEmpty()) return total * 0.10;

        return 0.0;
   }

   public static double calcSetOfThreeDisc(ArrayList<Product> cartList, double total) {
        long clothingCount = cartList
            .stream()
            .filter(e -> e instanceof Clothing)
            .count();
        
        long electronicCount = cartList
            .stream()
            .filter(e -> e instanceof Electronic)
            .count();
        
        if (clothingCount >= 3 || electronicCount >= 3) return total * 0.20;
        
        return 0.0;
    }

    public static void addProdToCart(Optional<Product> selectedProd, ArrayList<Product> cartList) {
        if (selectedProd.isPresent()) {
            try {
                Product p = selectedProd.get().extract(1);

                int index = cartList.indexOf(p);

                if (index == -1) cartList.add(p);
                
                else {
                    Product prodAtIndex = cartList.get(index);
                    prodAtIndex.setCount(prodAtIndex.getCount() + 1);
                }

            } catch (CloneNotSupportedException e) {
                System.out.println("Warning! Internal error occurred! Couldn't clone Object!");
            
            } catch (NotEnoughProductStockException e) {
                JOptionPane.showMessageDialog(null, "Cannot add item to cart, Not enough Stock!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void returnProdsFromCart(ArrayList<Product> prodList, ArrayList<Product> cartList) {
        prodList.stream()
                .filter(e -> cartList.contains(e))
                .forEach(e -> cartList.forEach(f -> {
                    if (e.getId().equals(f.getId())) e.setCount(e.getCount() + f.getCount());
        }));

        cartList.clear();
    }

    public static Optional<Product> getSelectedProd(WTable table) {
        int selectedRow = table.getSelectedRow();
        return getProdFromRow(selectedRow, table);
    }

    public static Optional<Product> getProdFromRow(int row, WTable table) {
        ItemTableModel itm = (ItemTableModel) table.getModel();
        return itm.getProductAtRow(row);
    }
}
