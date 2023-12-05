import java.util.ArrayList;

public class Client extends User {
    // All products bought by the customer
    private ArrayList<Product> purchaseHistory;

    public Client(String username, String password) {
        super(username, password);
        this.purchaseHistory = new ArrayList<>();
    }

    public ArrayList<Product> getPurchaseHistory() {
        return purchaseHistory;

    }

    public void setPurchaseHistory(ArrayList<Product> list) {
        this.purchaseHistory = list;
    }

    // Adds a single product to the product history
    public void addToPurchaseHistory(Product product) {
        purchaseHistory.add(product);
    }
}
