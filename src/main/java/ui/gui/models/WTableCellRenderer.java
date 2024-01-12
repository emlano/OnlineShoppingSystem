package ui.gui.models;

import java.awt.Color;
import java.awt.Component;
import java.util.Optional;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import lib.Product;

public class WTableCellRenderer extends DefaultTableCellRenderer {
    private final Color bg;
    private final Color fg;
    private final Color errorFg;
    private final Color errorBg;
    public WTableCellRenderer() {
        this.errorBg = Color.WHITE;
        this.errorFg = Color.RED;
        this.bg = new Color(122, 0, 228);
        this.fg = Color.WHITE;
        setHorizontalAlignment(JLabel.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if ((!isSelected) && isProductStockLow(row, table)) {
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

    public boolean isProductStockLow(int row, JTable table) {
        ItemTableModel itm = (ItemTableModel) table.getModel();
       Optional<Product> obj = itm.getProductAtRow(row);
       return obj.isPresent() && obj.get().getCount() < 3;
    }
}