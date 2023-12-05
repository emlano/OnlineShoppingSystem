import java.util.Comparator;

// Used to sort products by their product names
public class ProductNameComparator implements Comparator<Product> {
    @Override
    public int compare(Product first, Product second) {
        return first.getName().compareTo(second.getName());
    }
}
