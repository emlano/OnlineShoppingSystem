package ui.gui;

import lib.*;
import ui.gui.WComponents.WButton;
import ui.gui.WComponents.WComboBox;
import ui.gui.WComponents.WTable;
import ui.gui.models.ItemTableModel;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class GraphicalInterface {
    private final JFrame frame = new JFrame("Westminster Shopping Centre");
    private final String[] SELECTION_LIST = {"All", "Clothing", "Electronic"};
    private static JLabel[] productDetailsInfo = new JLabel[6];
    private static JLabel[] productDetailsText = new JLabel[6];
    private static WTable catalogueTable;

    private static ArrayList<Product> products;

    public GraphicalInterface(ArrayList<Product> productList) {
        products = productList;
        unsetSelectedProduct();

        JPanel topPanel = setupTopPanel();
        JPanel bottomPanel = setupBottomPanel();

        frame.setSize(1080, 720);
        frame.add(topPanel);
        frame.add(bottomPanel);
        frame.setLayout(new GridLayout(2, 1));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        bottomRibbon.add(addToCartButton);

        bottomPanel.add(productDetailsPanel);
        bottomPanel.add(addToCartPanel);

        return bottomPanel;
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
            
            } else {
                productDetailsText[i].setText("N/A");
                productDetailsInfo[i].setText("N/A");
            }
        }
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

        cartFrame.add(button);
        cartFrame.setSize(1080, 720);
        cartFrame.addWindowListener(wl);
        cartFrame.setVisible(true);
    }

    public ItemListener getComboBoxItemListener() {
        ItemListener il = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                redrawTable(e.getItem().toString());
            }
        };

        return il;
    }

    public WindowListener getWindowSwitchListener() {
        WindowListener wl = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
                start();
            }
        };

        return wl;
    }

    public ListSelectionListener getProductTableListSelectionListener() {
        ListSelectionListener lsl = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                WTable table = catalogueTable;
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    unsetSelectedProduct();
                    return;
                }

                String selectedProductId = table.getValueAt(selectedRow, 0).toString();

                Optional<Product> selectedProduct = products
                    .stream()
                    .filter(f -> f.getId().equals(selectedProductId))
                    .findFirst();

                if (selectedProduct.isPresent()) setSelectedProduct(selectedProduct.get());
            }
        };

        return lsl;
    }
}
