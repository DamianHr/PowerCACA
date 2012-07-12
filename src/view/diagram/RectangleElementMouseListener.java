package view.diagram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

class RectangleElementMouseListener implements MouseListener, MouseMotionListener {
    JComponent target;
    Point start_drag;
    Point start_loc;

    public RectangleElementMouseListener(JComponent target) {
        this.target = target;
    }

    public static JFrame getFrame(Container target) {
        if (target instanceof JFrame) {
            return (JFrame) target;
        }
        return getFrame(target.getParent());
    }

    public static JPanel getPanel(Container target) {
        if (target instanceof JPanel) {
            return (JPanel) target;
        }
        return getPanel(target.getParent());
    }

    Point getScreenLocation(MouseEvent e) {
        Point cursor = e.getPoint();
        Point target_location = this.target.getLocationOnScreen();
        return new Point((int) (target_location.getX() + cursor.getX()),
                (int) (target_location.getY() + cursor.getY()));
    }

    public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)) {
            RectangleElementContextMenu menu = new RectangleElementContextMenu((RectangleElement)target);
            menu.show(e.getComponent(), e.getX(), e.getY());
        }
        else if (e.getClickCount() == 2) {
            ((RectangleElement)target).editElement();
        }
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        this.start_drag = this.getScreenLocation(e);
        JPanel panel = RectangleElementMouseListener.getPanel(target);
        this.start_loc = panel.getLocation();
        FactoryPanel.setCurrentRectangleElement((RectangleElement) panel);
        FactoryPanel.bringElementToFront((RectangleElement) panel);

    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        Point current = this.getScreenLocation(e);
        Point offset = new Point((int) current.getX() - (int) start_drag.getX(),
                (int) current.getY() - (int) start_drag.getY());
        JPanel panel = RectangleElementMouseListener.getPanel(target);
        Point new_location = new Point(
                (int) (this.start_loc.getX() + offset.getX()), (int) (this.start_loc
                .getY() + offset.getY()));
        panel.setLocation(new_location);
        ((RectangleElement)panel).refreshAnchors();
    }

    public void mouseMoved(MouseEvent e) {

    }
}
