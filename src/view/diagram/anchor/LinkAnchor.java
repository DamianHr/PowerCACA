package view.diagram.anchor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;

/**
 * Created with IntelliJ IDEA.
 * User: asus
 * Date: 08/07/12
 * Time: 22:50
 * To change this template use File | Settings | File Templates.
 */
public class LinkAnchor extends JPanel {
    public LinkAnchor(Point pos, Boolean fixed) {
        this.setBackground(Color.BLACK);
        this.setSize(10,10);
        this.setOpaque(false);

        ElementMouseListener eml = new ElementMouseListener(this);
        this.addMouseListener(eml);
        this.addMouseMotionListener(eml);

        //Simulate the mouse pressed, to drag the panel after creation
        if(!fixed) {
            try {
                Robot r = new Robot();
                r.mousePress(InputEvent.BUTTON1_MASK);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public Point getCenter() {
        return new Point(this.getLocation().x + this.getSize().width, this.getLocation().y + this.getSize().height);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        Shape circle = new Ellipse2D.Float(0, 0, this.getHeight()-1, this.getWidth()-1);
        g2d.fill(circle);
        g2d.draw(circle);
        g.dispose();
    }
    static class ElementMouseListener implements MouseListener, MouseMotionListener {
        JComponent target;
        Point start_drag;
        Point start_loc;

        public ElementMouseListener (JComponent target) {
            this.target = target;
        }

        public JFrame getFrame(Container target) {
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
            JPanel panel = ElementMouseListener.getPanel(this.target);
            this.start_loc = panel.getLocation();
            //FactoryPanel.refreshLinkAnchors((LinkAnchor) panel, null);
        }

        public void mouseReleased(MouseEvent e) {
            //FactoryPanel.refreshLinkAnchors(null, null);
        }

        public void mouseDragged(MouseEvent e) {
            Point current = this.getScreenLocation(e);
            Point offset = new Point((int) current.getX() - (int) start_drag.getX(),
                    (int) current.getY() - (int) start_drag.getY());
            JPanel panel = ElementMouseListener.getPanel(target);
            Point new_location = new Point(
                    (int) (this.start_loc.getX() + offset.getX()), (int) (this.start_loc
                    .getY() + offset.getY()));
            panel.setLocation(new_location);
        }

        public void mouseMoved(MouseEvent e) {

        }
    }
}
