package ui.gui.models;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

import lib.Clothing;
import lib.Electronic;
import lib.Product;

public class CartTableModel extends AbstractTableModel {
    private final ArrayList<Product> rows;
    private final String[] columns = {"Product", "Quantity", "Price ($)"};

    public CartTableModel(ArrayList<Product> list) {
        this.rows = list;
    }

    @Override
    public int getColumnCount() {
        return this.columns.length;
    }

    @Override
    public int getRowCount() {
        return this.rows.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        Product obj = this.rows.get(row);

        String sizeOrBrand = obj instanceof Clothing 
            ? ((Clothing) obj).getSize().toString() 
            : ((Electronic) obj).getBrand();
        
        String colorOrWarranty = obj instanceof Clothing
            ? ((Clothing) obj).getColor()
            : String.valueOf(((Electronic) obj).getWarrantyPeriod());

        double productTotal = obj.getPrice() * obj.getCount();

        return switch(col) {
            case 0 -> "%s, %s, %s".formatted(obj.getId(), obj.getName(), sizeOrBrand, colorOrWarranty);
            case 1 -> "%d".formatted(obj.getCount());
            case 2 -> "%.2f".formatted(productTotal);
            default -> null;
        };
    }

    @Override
    public String getColumnName(int col) {
        return this.columns[col];
    }

    @Override
    public Class<String> getColumnClass(int col) {
        return String.class;
    }
}
