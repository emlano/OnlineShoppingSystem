package ui.gui;

import java.awt.Color;

import javax.swing.JComboBox;

public class WComboBox<E> extends JComboBox<E> {
    final Color HOVER_BG = new Color(55, 0, 179);
    final Color BG = new Color(122, 0, 228);
    final Color BLK = new Color(0, 0, 0);
    final Color WHT = new Color(255, 255, 255);

    public WComboBox(E[] items) {
        super(items);

        setBackground(BG);
        setForeground(WHT);
    }
}
