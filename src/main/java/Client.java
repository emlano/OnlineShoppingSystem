import java.util.ArrayList;

public class Client extends User {
    // Amount of items bought by this customer
    // used to track whether the customer is recurring customer;
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

    public void addToPurchaseHistory(Product product) {
        purchaseHistory.add(product);
    }
}
