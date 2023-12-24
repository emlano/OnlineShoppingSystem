package ui.gui;

import java.awt.*;
import javax.swing.*;

public class GraphicalInterface {
    private final JFrame frame = new JFrame("Westminster Shopping Centre");
    final JLabel SELECT_CATEGORY_TEXT = new JLabel("Select Product Category");
    final String[] SELECTION_LIST = {"All", "Clothing", "Electronic"};
    final WComboBox<String> comboBox = new WComboBox<>(SELECTION_LIST);
    final Color BG = new Color(122, 0, 228);
    final Color FG = new Color(255, 255, 255);

    public GraphicalInterface() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, 1));

        JPanel productCategoryContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        productCategoryContainer.add(SELECT_CATEGORY_TEXT);
        productCategoryContainer.add(comboBox);
        
        JPanel viewButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        WButton viewCartButton = new WButton("Shopping Cart");
        viewButtonPanel.add(viewCartButton);
        
        topPanel.add(viewButtonPanel);
        topPanel.add(productCategoryContainer);
        
        JPanel bottomPanel = new JPanel();
        WButton addToCartButton = new WButton("Add to Shopping Cart");
        bottomPanel.add(addToCartButton);
        bottomPanel.setBackground(Color.PINK);
        
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
