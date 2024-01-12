package lib;

import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList<Product> products;

    public ShoppingCart(ArrayList<Product> prodList) {
        this.products = prodList;
    }

    public ShoppingCart() {
        this(new ArrayList<>());
    }

    public ArrayList<Product> getProductList() {
        return products;
    }

    public void setProductList(ArrayList<Product> prodList) {
        this.products = prodList;
    }

    public void addToCart(Product prod) {
        int indexOfProd = this.products.indexOf(prod);

        if (indexOfProd == -1) this.products.add(prod);
        else {
            Product p = this.products.get(indexOfProd);
            p.setCount(p.getCount() + 1);
        }
    }

    public Product getProduct(int index) {
        return this.products.get(index);
    }

    public Product removeFromCart(int index) {
        return this.products.remove(index);
    }

    public int getSize() {
        return this.products.size();
    }

    public double calculateTotal() {
        return products
            .stream()
            .mapToDouble(Product::getPrice)
            .sum();
    }

    public double calculateNewUserDisc(Client client) {
        double total = calculateTotal();

        if (client.getPurchaseHistory().isEmpty()) return total * 0.10;
        return 0.0;
    }

    public double calculateThreeOfSetDisc() {
        double total = calculateTotal();

        long countOfClothing = products.stream().distinct().filter(e -> e instanceof Clothing).count();
        long countOfElectronic = products.stream().distinct().filter(e -> e instanceof Electronic).count();

        if (countOfClothing >= 3 || countOfElectronic >= 3) return total * 0.20;
        return 0.0;
    }

    public void emptyCart() {
        this.products = new ArrayList<>();
    }
}
