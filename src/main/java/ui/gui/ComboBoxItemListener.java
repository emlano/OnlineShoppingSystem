package ui.gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ComboBoxItemListener implements ItemListener {
    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        GraphicalInterface.redrawTable(itemEvent.getItem().toString());
    }
}
