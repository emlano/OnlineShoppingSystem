package ui.gui;

import lib.Client;
import lib.Clothing;
import lib.Electronic;
import lib.Product;
import lib.User;
import ui.gui.WComponents.WButton;
import ui.gui.WComponents.WComboBox;
import ui.gui.WComponents.WTable;
import ui.gui.models.ItemTableModel;
import ui.gui.models.WTableCellRenderer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;

import exceptions.NotEnoughProductStockException;

import java.awt.*;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class GraphicalInterface {
    private final JFrame frame = new JFrame("Westminster Shopping Centre");
    private final String[] SELECTION_LIST = {"All", "Clothing", "Electronic"};
    private static final JLabel[] productDetailsInfo = new JLabel[6];
    private static final JLabel[] productDetailsText = new JLabel[6];
    private static WTable catalogueTable;
    private static int displayCenterHeight;
    private static int displayCenterWidth;

    private static ArrayList<Product> products;
    private static ArrayList<Product> cart;
    private static Client client;

    public GraphicalInterface(User user, ArrayList<Product> productList) {
        products = productList;
        client = (Client) user;
        cart = new ArrayList<>();
        unsetSelectedProduct();

        GraphicsDevice gd = MouseInfo.getPointerInfo().getDevice();
        displayCenterWidth = gd.getDisplayMode().getWidth() / 2;
        displayCenterHeight = gd.getDisplayMode().getHeight() / 2;

        JPanel topPanel = setupTopPanel();
        JPanel bottomPanel = setupBottomPanel();

        frame.setSize(1080, 720);
        frame.add(topPanel);
        frame.add(bottomPanel);
        frame.setLayout(new GridLayout(2, 1));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setFrameToCenter(displayCenterWidth, displayCenterHeight, frame);
    }

    public void start() {
        frame.setVisible(true);
    }

    public JPanel setupTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JPanel viewButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        WButton viewCartButton = new WButton("Shopping Cart");

        viewCartButton.addActionListener(e -> openCartView());
        viewButtonPanel.add(viewCartButton);

        WComboBox<String> comboBox = new WComboBox<>(SELECTION_LIST);
        comboBox.addItemListener(getComboBoxItemListener());

        JPanel productCategoryContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        productCategoryContainer.add(new JLabel("Select Product Category"));
        productCategoryContainer.add(comboBox);

        ItemTableModel tableModel = new ItemTableModel(products);
        catalogueTable = new WTable(tableModel);
        catalogueTable
            .getSelectionModel()
            .addListSelectionListener(getProductTableListSelectionListener());
        catalogueTable.setDefaultRenderer(String.class, new WTableCellRenderer());
        
        JScrollPane tableScroller = new JScrollPane(catalogueTable);

        topPanel.add(viewButtonPanel);
        topPanel.add(productCategoryContainer);
        topPanel.add(tableScroller);

        return topPanel;
    }

    public JPanel setupBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        JPanel productDetailsPanel = new JPanel();
        productDetailsPanel.setLayout(new BoxLayout(productDetailsPanel, BoxLayout.Y_AXIS));
        productDetailsPanel.setBorder(new EmptyBorder(0, 50, 0, 0));

        JPanel productDetailsHeader = new JPanel();
        productDetailsHeader.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));
        productDetailsPanel.add(productDetailsHeader);
        productDetailsHeader.add(new JLabel("Selected Product - Details"));

        for (int i = 0; i < productDetailsText.length; i++) {
            JPanel j = new JPanel();

            j.add(productDetailsText[i]);
            j.add(new JLabel(" - "));
            j.add(productDetailsInfo[i]);

            j.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
            j.setBorder(new EmptyBorder(0, 25, 0, 0));
            productDetailsPanel.add(j);
        }

        JPanel addToCartPanel = new JPanel();
        addToCartPanel.setLayout(new BorderLayout());

        JPanel bottomRibbon = new JPanel();
        addToCartPanel.add(bottomRibbon, BorderLayout.SOUTH);
        addToCartPanel.setBorder(new EmptyBorder(0, 0, 25, 0));

        WButton addToCartButton = new WButton("Add to Shopping Cart");
        addToCartButton.addActionListener(e -> addProductToCart());
        bottomRibbon.add(addToCartButton);

        bottomPanel.add(productDetailsPanel);
        bottomPanel.add(addToCartPanel);

        return bottomPanel;
    }

    public void setFrameToCenter(int x, int y, JFrame frame) {
        frame.setLocation((x - frame.getWidth() / 2), (y - frame.getHeight() / 2));
    }

    public static void redrawTable(String type) {
        ArrayList<Product> list;

        if (type.equals("All")) {
            list = products;
        } else {
            list = products
                    .stream()
                    .filter(e -> e.getClass().getSimpleName().equals(type))
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        ItemTableModel tm = new ItemTableModel(list);
        GraphicalInterface.catalogueTable.setModel(tm);
    }

    public void unsetSelectedProduct() {
        for (int i = 0; i < productDetailsInfo.length; i++) {
            if (productDetailsInfo[i] == null) {
                productDetailsText[i] = new JLabel("N/A");
                productDetailsInfo[i] = new JLabel("N/A");

                setLabelFixedSize(productDetailsText[i], 120, 15);
            
            } else {
                productDetailsText[i].setText("N/A");
                productDetailsInfo[i].setText("N/A");
            }
        }
    }

    private void setLabelFixedSize(JLabel label, int width, int height) {
        label.setMinimumSize(new Dimension(width, height));
        label.setPreferredSize(new Dimension(width, height));
        label.setMaximumSize(new Dimension(width, height));
    }

    public static void setSelectedProduct(Product product) {
        switch (product.getClass().getSimpleName()) {
            case "Clothing" -> {
                Clothing c = (Clothing) product;
                String[] headers = {"Product ID", "Category", "Name", "Size", "Color", "Items Available"};
                String[] data = {c.getId(), "Clothing", c.getName(), c.getSize().toString(), c.getColor(), String.valueOf(c.getCount())};
                
                for (int i = 0; i < headers.length; i++) {
                    productDetailsText[i].setText(headers[i]);
                    productDetailsInfo[i].setText(data[i]);
                }
            }

            case "Electronic" -> {
                Electronic e = (Electronic) product;
                String[] headers = {"Product ID", "Category", "Name", "Brand", "Warranty in Weeks", "Items Available"};
                String[] data = {e.getId(), "Electronic", e.getName(), e.getBrand(), String.valueOf(e.getWarrantyPeriod()), String.valueOf(e.getCount())};

                for (int i = 0; i < headers.length; i++) {
                    productDetailsText[i].setText(headers[i]);
                    productDetailsInfo[i].setText(data[i]);
                }
            }
        }
    }

    public void openCartView() {
        WindowListener wl = getWindowSwitchListener();
        frame.setVisible(false);

        JFrame cartFrame = new JFrame("Shopping Cart");
        WButton button = new WButton("Test");

        button.addActionListener(e -> addToUserPurchaseHistory());

        cartFrame.add(button);
        cartFrame.setSize(1080, 720);
        cartFrame.addWindowListener(wl);
        cartFrame.setLocation(displayCenterWidth, displayCenterHeight);
        cartFrame.setVisible(true);
        setFrameToCenter(displayCenterWidth, displayCenterHeight, cartFrame);
    }

    private void addToUserPurchaseHistory() {
        cart.forEach(e -> client.addToPurchaseHistory(e));
        cart.clear();
    }

    private static double calculateTotal() {
        return cart.stream().mapToDouble(e -> e.getPrice()).sum();
    }

    private double calculateFirstCustomerDiscount() {
         if (client.getPurchaseHistory().isEmpty()) return calculateTotal() * 0.10;

         return 0.0;
    } 

    private double calculateSetOfThreeDiscount() {
        long clothingCount = cart
            .stream()
            .filter(e -> e.getClass().getSimpleName().equals("Clothing"))
            .count();
        
        long electronicCount = cart
            .stream()
            .filter(e -> e.getClass().getSimpleName().equals("Electronic"))
            .count();
        
        if (clothingCount >= 3 || electronicCount >= 3) return calculateTotal() * 0.20;
        
        return 0.0;
    }

    private void addProductToCart() {
        Optional<Product> product = getProductFromSelectedRow();
        if (product.isPresent()) {
            try {
                Product p = product.get().extract(1);
                cart.add(p);
                setSelectedProduct(product.get());
            } catch (CloneNotSupportedException e) {
                System.out.println("Warning! Internal error occurred! Couldn't clone Object!");
            
            } catch (NotEnoughProductStockException e) {
                JOptionPane.showMessageDialog(frame, "Cannot add item to cart, Not enough Stock!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void returnNotBoughtItems() {
        products.stream()
                .filter(e -> cart.contains(e))
                .forEach(e -> cart.forEach(f -> {
                    if (e.getId().equals(f.getId())) e.setCount(e.getCount() + f.getCount());
        }));

        cart.clear();
    }

    public ItemListener getComboBoxItemListener() {
        return e -> redrawTable(e.getItem().toString());
    }

    public WindowListener getWindowSwitchListener() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
                returnNotBoughtItems();
                redrawTable("All");
                unsetSelectedProduct();
                start();
            }
        };
    }

    public ListSelectionListener getProductTableListSelectionListener() {
        return e -> {
            Optional<Product> selectedProduct = getProductFromSelectedRow();
            if (selectedProduct.isPresent()) setSelectedProduct(selectedProduct.get());
            else unsetSelectedProduct();
        };
    }

    private Optional<Product> getProductFromSelectedRow() {
            int selectedRow = catalogueTable.getSelectedRow();
            return getProductFromRow(selectedRow);
    }

    public static Optional<Product> getProductFromRow(int row) {
        if (row == -1) return Optional.empty();

        String productId = catalogueTable.getValueAt(row, 0).toString();

        return products
            .stream()
            .filter(e -> e.getId().equals(productId))
            .findFirst();
    }
}
