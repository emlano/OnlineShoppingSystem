package ui.gui;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

import lib.*;

public class ItemTableModel extends AbstractTableModel {
    private final ArrayList<Product> rows;
    private final String[] columns = {"Product ID", "Name", "Category", "Price ($)", "Info"};
    
    public ItemTableModel(ArrayList<Product> products) {
        this.rows = products;
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
    public Object getValueAt(int row, int column) {
        Product obj = this.rows.get(row);

        return switch(column) {
            case 0 -> obj.getId();
            case 1 -> obj.getName();
            case 2 -> obj.getClass().getSimpleName();
            case 3 -> "%.2f".formatted(obj.getPrice());
            case 4 -> obj.getClass().getSimpleName().equals("Clothing")
                        ? "%s, %s"
                            .formatted(
                                ((Clothing) obj).getSize(),
                                ((Clothing) obj).getColor()
                            )
                        
                        : "%s, %s weeks warranty"
                            .formatted(
                                ((Electronic) obj).getBrand(),
                                ((Electronic) obj).getWarrantyPeriod()
                            );
            default -> null;
        };
    }

    @Override
    public String getColumnName(int col) {
        return this.columns[col];
    }

    @Override
    public Class<?> getColumnClass(int col) {
        return String.class;
    }
}
