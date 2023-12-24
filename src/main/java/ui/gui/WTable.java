package ui.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

public class WTable extends JTable {
    public WTable(TableModel model) {
        super(model);
        setAutoCreateRowSorter(true);
        getTableHeader().setReorderingAllowed(false);
        DefaultTableCellRenderer dtc = new DefaultTableCellRenderer();
        dtc.setHorizontalAlignment(JLabel.CENTER);
        setDefaultRenderer(String.class, dtc);
    }
}
