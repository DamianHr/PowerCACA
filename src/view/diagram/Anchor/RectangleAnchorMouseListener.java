package view.diagram.Anchor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created with IntelliJ IDEA.
 * User: asus
 * Date: 09/07/12
 * Time: 21:18
 * To change this template use File | Settings | File Templates.
 */
public class RectangleAnchorMouseListener implements MouseMotionListener, MouseListener{
    JComponent target;
    Point start_drag;
    Point start_loc;

    public RectangleAnchorMouseListener(JComponent target) {
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
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        this.start_drag = this.getScreenLocation(e);
        JPanel panel = RectangleAnchorMouseListener.getPanel(target);
        this.start_loc = panel.getLocation();
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        Point current = this.getScreenLocation(e);
        Point offset = new Point((int) current.getX() - (int) start_drag.getX(),
                (int) current.getY() - (int) start_drag.getY());
        JPanel panel = RectangleAnchorMouseListener.getPanel(target);
        Point new_location = new Point(
                (int) (this.start_loc.getX() + offset.getX()), (int) (this.start_loc
                .getY() + offset.getY()));
        panel.setLocation(new_location);
        ((RectangleAnchor)panel).startResizeTable();
    }

    public void mouseMoved(MouseEvent e) {
    }
}
