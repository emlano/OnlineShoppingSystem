package lib;

import java.util.ArrayList;

public class Client extends User {
    // All products bought by the customer
    private ArrayList<Product> purchaseHistory;
    // Products added to cart by the user but was not purchased
    private ShoppingCart cart;

    public Client(String username, String password) {
        super(username, password);
        this.purchaseHistory = new ArrayList<>();
        this.cart = new ShoppingCart();
    }

    public ArrayList<Product> getPurchaseHistory() {
        return purchaseHistory;

    }

    public void setPurchaseHistory(ArrayList<Product> list) {
        this.purchaseHistory = list;
    }

    // Adds a single product to the product history
    public void addToPurchaseHistory(Product product) {
        int indexOfItem = purchaseHistory.indexOf(product);

        if (indexOfItem == -1) purchaseHistory.add(product);
        
        else {
            Product p = purchaseHistory.get(indexOfItem);
            p.setCount(p.getCount() + 1);
        }
    }

    public void addToPurchaseHistory(ArrayList<Product> prodList) {
        for (Product i : prodList) addToPurchaseHistory(i);
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }
}
