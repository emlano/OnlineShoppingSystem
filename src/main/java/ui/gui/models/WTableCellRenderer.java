package ui.gui.models;

import java.awt.Color;
import java.awt.Component;
import java.util.Optional;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import lib.Product;
import ui.gui.GraphicalInterface;

public class WTableCellRenderer extends DefaultTableCellRenderer {
    private Color bg;
    private Color fg;
    private Color errorFg;
    private Color errorBg;
    public WTableCellRenderer() {
        this.errorBg = Color.WHITE;
        this.errorFg = Color.RED;
        this.bg = new Color(122, 0, 228);
        this.fg = Color.WHITE;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if ((!isSelected) && isProductStockLow(row)) {
            c.setBackground(errorBg);
            c.setForeground(errorFg);
        
        } else if (isSelected) {
            c.setBackground(bg);
            c.setForeground(fg);
        
        } else {
            c.setBackground(table.getBackground());
            c.setForeground(table.getForeground());
        }

        return c;
    }

    public boolean isProductStockLow(int row) {
       Optional<Product> obj = GraphicalInterface.getProductFromRow(row);
       return obj.isPresent() && obj.get().getCount() < 3;
    }
}