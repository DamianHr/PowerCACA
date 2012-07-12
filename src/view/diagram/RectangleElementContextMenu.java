package view.diagram;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 */
public class RectangleElementContextMenu extends JPopupMenu {
    RectangleElement target;
    JMenuItem editItem;
    JMenuItem deleteItem;

    public RectangleElementContextMenu(RectangleElement target) {
        this.target = target;

        editItem = new JMenuItem("Edit");
        editItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RectangleElementContextMenu.this.target.editElement();
            }
        });
        this.add(editItem);

        deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RectangleElementContextMenu.this.target.deleteElement();
            }
        });
        this.add(deleteItem);
    }
}
