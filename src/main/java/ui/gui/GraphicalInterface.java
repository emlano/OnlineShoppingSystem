package ui.gui;

import lib.Client;
import lib.Product;
import lib.User;
import ui.gui.WComponents.WButton;
import ui.gui.WComponents.WComboBox;
import ui.gui.WComponents.WTable;
import ui.gui.models.CartTableModel;
import ui.gui.models.ItemTableModel;
import ui.gui.models.WTableCellRenderer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowListener;
import java.util.ArrayList;

// Sets up the swing components
public class GraphicalInterface {
    private final JFrame FRAME = new JFrame("Westminster Shopping Centre");
    private final String[] SELECTION_LIST = {"All", "Clothing", "Electronic"};
    private final JLabel[] PROD_DESC_TXT_LABELS = new JLabel[6]; // Product description labels at the bottom panel of main page
    private final JLabel[] PROD_DESC_DATA_LABELS = new JLabel[6]; // Same as above but contains data about the selected prod
    
    private JFrame cartFrame;
    private static WTable catalogueTable;
    private static int displayCenterHeight;
    private static int displayCenterWidth;

    private static ArrayList<Product> products;
    private static Client client;

    public GraphicalInterface(User user, ArrayList<Product> productList) {
        products = productList;
        client = (Client) user;
        // Set prod desc labels to "N/A"
        GraphicalLogic.setDefaultProdDescLabels(PROD_DESC_TXT_LABELS, PROD_DESC_DATA_LABELS);

        // To spawn windows in the middle of the screen rather than in a corner
        GraphicsDevice gd = MouseInfo.getPointerInfo().getDevice();
        displayCenterWidth = gd.getDisplayMode().getWidth() / 2;
        displayCenterHeight = gd.getDisplayMode().getHeight() / 2;

        JPanel topPanel = setupTopPanel();
        JPanel bottomPanel = setupBottomPanel();

        FRAME.setSize(1080, 720);
        FRAME.add(topPanel);
        FRAME.add(bottomPanel);
        FRAME.setLayout(new GridLayout(2, 1));
        FRAME.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GraphicalLogic.setFrameToCenter(displayCenterWidth, displayCenterHeight, FRAME);
    }

    public void start() {
        FRAME.setVisible(true);
    }

    public JPanel setupTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JPanel viewButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        WButton viewCartButton = new WButton("Shopping Cart");

        viewButtonPanel.add(viewCartButton);
        
        WComboBox<String> comboBox = new WComboBox<>(SELECTION_LIST);
        
        JPanel productCategoryContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        productCategoryContainer.add(new JLabel("Select Product Category"));
        productCategoryContainer.add(comboBox);
        
        ItemTableModel tableModel = new ItemTableModel(products);
        catalogueTable = new WTable(tableModel);
        
        catalogueTable.setDefaultRenderer(String.class, new WTableCellRenderer());
        JScrollPane tableScroller = new JScrollPane(catalogueTable);
        
        topPanel.add(viewButtonPanel);
        topPanel.add(productCategoryContainer);
        topPanel.add(tableScroller);
        
        catalogueTable
        .getSelectionModel()
        .addListSelectionListener(EventListeners.getTableListSelectionListener(catalogueTable, PROD_DESC_TXT_LABELS, PROD_DESC_DATA_LABELS));
        
        comboBox.addItemListener(EventListeners.getComboBoxItemListener(products, catalogueTable));
        viewCartButton.addActionListener(e -> openCartView());

        
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

        for (int i = 0; i < PROD_DESC_DATA_LABELS.length; i++) {
            JPanel j = new JPanel();

            j.add(PROD_DESC_TXT_LABELS[i]);
            j.add(new JLabel(" - "));
            j.add(PROD_DESC_DATA_LABELS[i]);

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
        
        addToCartButton.addActionListener(e -> EventListeners.armAddToCartButton(catalogueTable, client, PROD_DESC_TXT_LABELS, PROD_DESC_DATA_LABELS));
        
        return bottomPanel;
    }

    public void openCartView() {
        WindowListener wl = EventListeners.getCartCloseListener(catalogueTable, PROD_DESC_TXT_LABELS, PROD_DESC_DATA_LABELS, FRAME);
        FRAME.setVisible(false);
        
        cartFrame = new JFrame("Shopping Cart");
        cartFrame.setLayout(new GridLayout(3, 1));
        cartFrame.setSize(1080, 720);
        GraphicalLogic.setFrameToCenter(displayCenterWidth, displayCenterHeight, cartFrame);

        cartFrame.add(setupCartTopPanel());
        cartFrame.add(setupCartBottomPanel());
        cartFrame.add(setupCartBottomRibbon());

        cartFrame.addWindowListener(wl);

        cartFrame.setVisible(true);
    }

    public JPanel setupCartTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        CartTableModel ctm = new CartTableModel(client.getCart().getProductList());
        WTable cartTable = new WTable(ctm);

        // To make table unselectedable and undraggable
        cartTable.setEnabled(false);
        JScrollPane tableScroll = new JScrollPane(cartTable);

        topPanel.add(tableScroll);
        return topPanel;
    }

    public JPanel setupCartBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        //Gets total withotu discounts
        double total = client.getCart().calculateTotal();
        double firstDiscount = client.getCart().calculateNewUserDisc(client);
        double secondDiscount = client.getCart().calculateThreeOfSetDisc();

        final String[] headers = {"Total", "First Purchase Discount (10%)", "Three Items in Same Category (20%)", "Final Total"};
        final String[] data = {
            "$ %.2f".formatted(total),
            "$ %.2f".formatted(firstDiscount),
            "$ %.2f".formatted(secondDiscount),
            "$ %.2f".formatted(total - (firstDiscount + secondDiscount))
        };

        for (int i = 0; i < headers.length; i++) {
            JPanel p = new JPanel();
            p.setLayout(new FlowLayout(FlowLayout.RIGHT));

            JLabel headLabel = new JLabel(headers[i]);
            JLabel dataLabel = new JLabel(data[i]);

            // Set all labels to a fixed size, so they align with each other no matter the text width
            GraphicalLogic.setLabelFixedSize(headLabel, 250, 15);
            GraphicalLogic.setLabelFixedSize(dataLabel, 100, 15);

            p.add(headLabel);
            p.add(new JLabel(" - "));
            p.add(dataLabel);
            
            bottomPanel.add(p);
        }

        return bottomPanel;
    }

    // Sets up the clear cart and checkout buttons
    public JPanel setupCartBottomRibbon() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JPanel buttonHolder = new JPanel();
        buttonHolder.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));

        WButton clearCart = new WButton("Clear Cart");
        WButton checkout = new WButton("Checkout");

        
        buttonHolder.add(clearCart);
        buttonHolder.add(checkout);
        
        panel.add(buttonHolder, BorderLayout.SOUTH);

        clearCart.addActionListener(e -> {
            EventListeners.killCartWindow(cartFrame, catalogueTable, PROD_DESC_TXT_LABELS, PROD_DESC_DATA_LABELS);
            EventListeners.clearCart(FRAME, products, client, catalogueTable, PROD_DESC_TXT_LABELS, PROD_DESC_DATA_LABELS);
        });

        checkout.addActionListener(e -> {
            EventListeners.killCartWindow(cartFrame, catalogueTable, PROD_DESC_TXT_LABELS, PROD_DESC_DATA_LABELS);
            EventListeners.checkout(client, FRAME);
        });

        return panel;
    }
}
