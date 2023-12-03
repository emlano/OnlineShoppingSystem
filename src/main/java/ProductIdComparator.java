import java.util.Comparator;

public class ProductIdComparator implements Comparator<Product> {
    @Override
    public int compare(Product first, Product second) {
        return first.getId().compareTo(second.getId());
    }
}
