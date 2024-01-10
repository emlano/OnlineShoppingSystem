package ui.gui.models;

import lib.Clothing;
import lib.Electronic;
import lib.Product;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Optional;

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

    public Optional<Product> getProductAtRow(int row) {
        if ((row < 0 || row > getRowCount()) || this.rows.get(row) == null) return Optional.empty();
        return Optional.of(this.rows.get(row));
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
