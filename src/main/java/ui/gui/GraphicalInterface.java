package ui.gui;

import lib.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.AreaAveragingScaleFilter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class GraphicalInterface {
    private final JFrame frame = new JFrame("Westminster Shopping Centre");
    private static JLabel productId = new JLabel("N/A");
    private static JLabel category = new JLabel("N/A");
    private static JLabel name = new JLabel("N/A");
    private static JLabel sizeOrBrand = new JLabel("N/A");
    private static JLabel sizeOrBrandText = new JLabel("N/A - ");
    private static JLabel colorOrWarranty = new JLabel("N/A");
    private static JLabel colorOrWarrantyText = new JLabel("N/A - ");
    private static JLabel itemCount = new JLabel("N/A");
    private final String[] SELECTION_LIST = {"All", "Clothing", "Electronic"};
    private final Color BG = new Color(122, 0, 228);
    private final Color FG = new Color(255, 255, 255);
    private static WTable catalogueTable;

    public static ArrayList<Product> products;

    public GraphicalInterface(ArrayList<Product> productList) {
        products = productList;
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JPanel viewButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        WButton viewCartButton = new WButton("Shopping Cart");
        viewButtonPanel.add(viewCartButton);

        WComboBox<String> comboBox = new WComboBox<>(SELECTION_LIST);
        JPanel productCategoryContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        productCategoryContainer.add(new JLabel("Select Product Category"));
        productCategoryContainer.add(comboBox);

        comboBox.addItemListener(new ComboBoxItemListener());

        ItemTableModel tableModel = new ItemTableModel(productList);
        catalogueTable = new WTable(tableModel);
        JScrollPane tableScroller = new JScrollPane(catalogueTable);

        catalogueTable.addMouseListener(new TableItemListener());

        topPanel.add(viewButtonPanel);
        topPanel.add(productCategoryContainer);
        topPanel.add(tableScroller);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        JPanel productDetailsPanel = new JPanel();
        productDetailsPanel.setLayout(new BoxLayout(productDetailsPanel, BoxLayout.Y_AXIS));

        JPanel productDetailsTextContainer = new JPanel();
        productDetailsTextContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        productDetailsPanel.add(productDetailsTextContainer);
        productDetailsTextContainer.add(new JLabel("Selected Product - Details"));

        JLabel[] productPanels = {productId, category, name, sizeOrBrand, colorOrWarranty, itemCount};
        JLabel[] productTexts = {
                        new JLabel("Product ID - "),
                        new JLabel("Category - "),
                        new JLabel("Name - "),
                        sizeOrBrandText,
                        colorOrWarrantyText,
                        new JLabel("Items Available - ")
        };

        for (int i = 0; i < productPanels.length; i++) {
            JPanel j = new JPanel();
            j.add(productTexts[i]);
            j.add(productPanels[i]);
            j.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
            productDetailsPanel.add(j);
        }

        JPanel addToCartPanel = new JPanel();
        addToCartPanel.setLayout(new BorderLayout());

        JPanel bottomRibbon = new JPanel();
        addToCartPanel.add(bottomRibbon, BorderLayout.SOUTH);

        WButton addToCartButton = new WButton("Add to Shopping Cart");
        bottomRibbon.add(addToCartButton);

        bottomPanel.add(productDetailsPanel);
        bottomPanel.add(addToCartPanel);

        frame.setSize(1080, 720);
        frame.add(topPanel);
        frame.add(bottomPanel);
        frame.setLayout(new GridLayout(2, 1));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void start() {
        frame.setVisible(true);
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

    public static void setSelectedProduct(Product product) {
        productId.setText(product.getId());
        category.setText(product.getClass().getSimpleName());
        name.setText(product.getName());

        if (product.getClass().getSimpleName().equals("Clothing")) {
            Clothing c = (Clothing) product;
            sizeOrBrandText.setText("Size - ");
            sizeOrBrand.setText(c.getSize().toString());
            colorOrWarrantyText.setText("Color - ");
            colorOrWarranty.setText(c.getColor());

        } else {
            Electronic e = (Electronic) product;
            sizeOrBrandText.setText("Brand - ");
            sizeOrBrand.setText(e.getBrand());
            colorOrWarrantyText.setText("Warranty in Weeks - ");
            colorOrWarranty.setText(String.valueOf(e.getWarrantyPeriod()));
        }

        itemCount.setText(String.valueOf(product.getCount()));
    }
}
