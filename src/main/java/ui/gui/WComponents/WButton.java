package ui.gui.WComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WButton extends JButton {
    final Color HOVER_BG = new Color(55, 0, 179);
    final Color BG = new Color(122, 0, 228);
    final Color BLK = new Color(0, 0, 0);
    final Color WHT = new Color(255, 255, 255);

    public WButton(String text) {
        super(text);
        setBackground(BG);
        setForeground(WHT);
        setFocusPainted(false);

        setSize(text.length() + 4, 10);

        getModel().addChangeListener(e -> {
            
        });

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                setBackground(HOVER_BG);
            }

            public void mouseExited(MouseEvent e) {
                setBackground(BG);
            }

            public void mousePressed(MouseEvent e) {
                setBackground(WHT);
                setForeground(BLK);
            }

            public void mouseReleased(MouseEvent e) {
                setBackground(BG);
                setForeground(WHT);
            }
        });
    }
}
