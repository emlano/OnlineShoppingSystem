import java.util.ArrayList;
import java.util.Arrays;

import comparators.ProductIdComparator;
import comparators.ProductNameComparator;
import lib.Product;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing class Product")
public class ProductTest {

    @Test
    @DisplayName("Product class must exist")
    public void productClassExists() {
        try {
            Class.forName("lib.Product");
        } catch (Exception e) {
            Assertions.fail("'Product' class was not found!");
        }
    }

    @Test
    @DisplayName("Testing Product class's Getters")
    public void productGettersWork() {
        SubProduct sb = new SubProduct("abcdef", "SubProduct", 199.99, 10);
        
        assertEquals("abcdef", sb.getId());
        assertEquals("SubProduct", sb.getName());
        assertEquals(199.99, sb.getPrice(), 0.0);
        assertEquals(10, sb.getCount());
    }

    @Test
    @DisplayName("Testing Product class's Setters")
    public void productSettersWork() {
        SubProduct sb = new SubProduct("abcdef", "SubProduct", 199.99, 10);

        sb.setId("defabc");
        sb.setName("ProductSub");
        sb.setPrice(399.99);
        sb.setCount(1);

        assertEquals("defabc", sb.getId());
        assertEquals("ProductSub", sb.getName());
        assertEquals(399.99, sb.getPrice(), 0.0);
        assertEquals(1, sb.getCount());
    }

    @Test
    @DisplayName("Testing equals method of Product class")
    public void productEqualsMethodWorks() {
        SubProduct sb = new SubProduct("abcdef", "SubProduct", 199.99, 10);
        SubProduct other = new SubProduct("abcdef", "SubProduct", 199.99, 10);
        SubProduct another = new SubProduct("defghk", "SubProduct", 199.99, 11);

        assertEquals(sb, other);
        assertNotEquals(sb, another);
    }

    @Test
    @DisplayName("Testing toString method of Product class")
    public void productToStringMethodWorks() {
        SubProduct sb = new SubProduct("abcdef", "SubProduct", 199.99, 10);

        assertEquals("SubProduct", sb.toString());
    }

    @Test
    @DisplayName("Testing Product comparators")
    public void productComparatorsWork() {
        SubProduct sb1 = new SubProduct("AAAA", "AAAA", 0, 0);
        SubProduct sb2 = new SubProduct("AAA", "AAA", 0, 0);
        SubProduct sb3 = new SubProduct("AA", "AA", 0, 0);
        SubProduct sb4 = new SubProduct("A", "A", 0, 0);
        SubProduct sb5 = new SubProduct("ABCD", "ABCD", 0, 0);
        SubProduct sb6 = new SubProduct("ABBB", "ABBB", 0, 0);
        SubProduct sb7 = new SubProduct("ABC", "ABC", 0, 0);

        ArrayList<SubProduct> anotherList;

        ArrayList<SubProduct> sorted = new ArrayList<>(Arrays.asList(sb4, sb3, sb2, sb1, sb6, sb7, sb5));
        ArrayList<SubProduct> list = new ArrayList<>(Arrays.asList(sb1, sb2, sb3, sb4, sb5, sb6, sb7));

        anotherList = list;

        list.sort(new ProductIdComparator());
        anotherList.sort(new ProductNameComparator());

        assertEquals(sorted, list);
        assertEquals(sorted, anotherList);
    }
}

class SubProduct extends Product {
    public SubProduct(String id, String name, double price, int count) {
        super(id, name, price, count);
    }
}