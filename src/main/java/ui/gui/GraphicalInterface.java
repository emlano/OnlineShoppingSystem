package ui.gui;

import lib.*;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class GraphicalInterface {
    private final JFrame frame = new JFrame("Westminster Shopping Centre");
    private JLabel productId = new JLabel("N/A");
    private JLabel category = new JLabel("N/A");
    private JLabel name = new JLabel("N/A");
    private JLabel size = new JLabel("N/A");
    private JLabel color = new JLabel("N/A");
    private JLabel itemCount = new JLabel("N/A");
    private final String[] SELECTION_LIST = {"All", "Clothing", "Electronic"};
    private final WComboBox<String> comboBox = new WComboBox<>(SELECTION_LIST);
    private final Color BG = new Color(122, 0, 228);
    private final Color FG = new Color(255, 255, 255);
    private ArrayList<Product> products;

    public GraphicalInterface(ArrayList<Product> productList) {
        this.products = productList;
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JPanel viewButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        WButton viewCartButton = new WButton("Shopping Cart");
        viewButtonPanel.add(viewCartButton);

        JPanel productCategoryContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        productCategoryContainer.add(new JLabel("Select Product Category"));
        productCategoryContainer.add(comboBox);

        ItemTableModel tableModel = new ItemTableModel(productList);
        WTable catalogueTable = new WTable(tableModel);
        JScrollPane tableScroller = new JScrollPane(catalogueTable);

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

        JLabel[] productPanels = {productId, category, name, size, color, itemCount};
        final String[] productTexts = {"Product ID", "Category", "Name", "Size", "Color", "Items Available"};

        for (int i = 0; i < productPanels.length; i++) {
            JPanel j = new JPanel();
            j.add(new JLabel(productTexts[i] + " - "));
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
}
