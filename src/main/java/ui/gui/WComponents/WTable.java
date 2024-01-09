package ui.gui.WComponents;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;

public class WTable extends JTable {
    public WTable(TableModel model) {
        super(model);
        setAutoCreateRowSorter(true);
        getTableHeader().setReorderingAllowed(false);
        DefaultTableCellRenderer dtc = new DefaultTableCellRenderer();
        dtc.setHorizontalAlignment(JLabel.CENTER);
        setDefaultRenderer(String.class, dtc);
        setSelectionBackground(new Color(122, 0, 228));
        setSelectionForeground(Color.white);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}
