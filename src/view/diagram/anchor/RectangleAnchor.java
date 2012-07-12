package view.diagram.anchor;

import view.diagram.RectangleElement;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class RectangleAnchor extends JPanel {

    static public enum TYPE {
        NORTH_WEST, NORTH_MIDDLE, NORTH_EAST,
        MIDDLE_WEST, MIDDLE_EAST,
        SOUTH_WEST, SOUTH_MIDDLE, SOUTH_EAST
    };

    private RectangleElement associatedRectangleElement;
    private TYPE type;
    public TYPE getType() {
        return this.type;
    }

    public RectangleAnchor(RectangleElement rectangleElement, TYPE type, Point pos) {
        this.setBackground(Color.BLACK);
        this.setSize(8,8);

        this.associatedRectangleElement = rectangleElement;
        this.type = type;

        RectangleAnchorMouseListener rectangleAnchorMouseListener = new RectangleAnchorMouseListener(this);
        this.addMouseListener(rectangleAnchorMouseListener);
        this.addMouseMotionListener(rectangleAnchorMouseListener);

        setAnchorLocation(pos);
    }

    public void startResizeTable() {
        this.associatedRectangleElement.resize(this);
    }

    public void setAnchorLocation(Point p) {
        this.setLocation(new Point( p.x - this.getWidth() /2 , p.y - this.getHeight() / 2));
    }
}
