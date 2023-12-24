package ui.gui;

import lib.Product;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Optional;

public class TableItemListener implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {}

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        WTable table = ((WTable) mouseEvent.getSource());
        int row = table.getSelectedRow();
        String productId = table.getValueAt(row, 0).toString();

        Optional<Product> selectedProduct = GraphicalInterface.products
                    .stream()
                    .filter(e -> e.getId().equals(productId))
                    .findFirst();

        if (selectedProduct.isPresent()) {
            GraphicalInterface.setSelectedProduct(selectedProduct.get());
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        mousePressed(mouseEvent);
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {}
}
