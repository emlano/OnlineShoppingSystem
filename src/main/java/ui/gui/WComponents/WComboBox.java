package ui.gui.WComponents;

import javax.swing.*;
import java.awt.*;

public class WComboBox<E> extends JComboBox<E> {
    final Color BG = new Color(122, 0, 228);
    final Color WHT = new Color(255, 255, 255);

    public WComboBox(E[] items) {
        super(items);

        setBackground(BG);
        setForeground(WHT);
    }
}
