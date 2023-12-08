package comparators;

import lib.Product;

import java.util.Comparator;

// Used to sort products by their product IDs
public class ProductIdComparator implements Comparator<Product> {
    @Override
    public int compare(Product first, Product second) {
        return first.getId().compareTo(second.getId());
    }
}
