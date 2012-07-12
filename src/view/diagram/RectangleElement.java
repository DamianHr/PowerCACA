package view.diagram;

import org.json.simple.JSONObject;
import view.diagram.anchor.RectangleAnchor;

import java.awt.*;
import java.util.ArrayList;

/**
 * Abstract Class, extends of DiagramElement, content the methode used by
 * some elements of the diagram
 * @author dhercun
 */
public abstract class RectangleElement extends DiagramElement{

    /**
     * The owner o the current element
     */
    protected FactoryPanel factoryPanel;
    protected FactoryPanel getFactoryPanel() {
        return factoryPanel;
    }

    /**
     * The Anchor collection of the current element
     */
    protected ArrayList<RectangleAnchor> anchors;
    protected ArrayList<RectangleAnchor> getAnchors() {
        return this.anchors;
    }
    protected void setAnchors(ArrayList<RectangleAnchor> anchors) {
        this.anchors = anchors;
    }

    /**
     * Constructor of the RectangleElement, used by childs
     * @param factoryPanel The
     */
    public RectangleElement(FactoryPanel factoryPanel) {
       this.factoryPanel = factoryPanel;

        RectangleElementMouseListener reml = new RectangleElementMouseListener(this);
        this.addMouseListener(reml);
        this.addMouseMotionListener(reml);
    }

    /**
     * Create the RectangleAnchor elements of the current element
     * @see RectangleAnchor
     */
    protected void initAnchors() {
        this.anchors.add(new RectangleAnchor(this, RectangleAnchor.TYPE.NORTH_WEST, new Point(this.getX(), this.getY())));
        this.anchors.add(new RectangleAnchor(this, RectangleAnchor.TYPE.NORTH_MIDDLE,new Point(this.getX(), this.getY())));
        this.anchors.add(new RectangleAnchor(this, RectangleAnchor.TYPE.NORTH_EAST,new Point(this.getX(), this.getY())));
        this.anchors.add(new RectangleAnchor(this, RectangleAnchor.TYPE.MIDDLE_EAST,new Point(this.getX(), this.getY())));
        this.anchors.add(new RectangleAnchor(this, RectangleAnchor.TYPE.SOUTH_EAST,new Point(this.getX(), this.getY())));
        this.anchors.add(new RectangleAnchor(this, RectangleAnchor.TYPE.SOUTH_MIDDLE,new Point(this.getX(), this.getY())));
        this.anchors.add(new RectangleAnchor(this, RectangleAnchor.TYPE.SOUTH_WEST,new Point(this.getX(), this.getY())));
        this.anchors.add(new RectangleAnchor(this, RectangleAnchor.TYPE.MIDDLE_WEST,new Point(this.getX(), this.getY())));
    }

    /**
     * Refresh the position of the RectangleAnchor elements of the current element
     * @see RectangleAnchor
     */
    public void refreshAnchors() {
        this.anchors.get(0).setAnchorLocation(new Point(this.getX(), this.getY()));
        this.anchors.get(1).setAnchorLocation(new Point(this.getX() + this.getWidth() / 2, this.getY()));
        this.anchors.get(2).setAnchorLocation(new Point(this.getX() + this.getWidth(), this.getY()));
        this.anchors.get(3).setAnchorLocation(new Point(this.getX() + this.getWidth(), this.getY() + this.getHeight() / 2));
        this.anchors.get(4).setAnchorLocation(new Point(this.getX() + this.getWidth(), this.getY() + this.getHeight()));
        this.anchors.get(5).setAnchorLocation(new Point(this.getX() + this.getWidth() / 2, this.getY() + this.getHeight()));
        this.anchors.get(6).setAnchorLocation(new Point(this.getX(), this.getY() + this.getHeight()));
        this.anchors.get(7).setAnchorLocation(new Point(this.getX(), this.getY() + this.getHeight() / 2));
    }

    /**
     * Set the visibility boolean of the RectangleAnchor elements of the current element
     * @param visible The new value of the anchors visibility
     */
    protected void setAnchorsVisibility(Boolean visible) {
        for(int i = 0; i < 8; i++) {
            this.anchors.get(i).setVisible(visible);
        }
    }

    /**
     * Resize the current element
     * @param anchor The RectangleAnchor which is moved by the user
     */
    public void resize(RectangleAnchor anchor) {
        if(RectangleAnchor.TYPE.NORTH_WEST== anchor.getType()) {
            Point mark = new Point(this.getX() + this.getWidth(), this.getY() + this.getHeight());
            this.setLocation(anchor.getLocation());
            this.setSize(((Double)mark.getX()).intValue() - anchor.getX(), ((Double)mark.getY()).intValue() - anchor.getY());
        }
        else if(RectangleAnchor.TYPE.NORTH_MIDDLE == anchor.getType()) {
            Point mark = new Point(this.getX() + this.getWidth() / 2, this.getY() + this.getHeight());
            this.setLocation(this.getX(), anchor.getY());
            this.setSize(this.getWidth(), ((Double)mark.getY()).intValue() - anchor.getY());
        }
        else if(RectangleAnchor.TYPE.NORTH_EAST == anchor.getType()) {
            Point mark = new Point(this.getX(), this.getY() + this.getHeight());
            this.setLocation(this.getX(), anchor.getY());
            this.setSize(anchor.getX() - ((Double)mark.getX()).intValue(), ((Double)mark.getY()).intValue() - anchor.getY());
        }
        else if(RectangleAnchor.TYPE.MIDDLE_EAST == anchor.getType()) {
            Point mark = new Point(this.getX(), this.getY() + this.getHeight());
            this.setSize(anchor.getX() - ((Double)mark.getX()).intValue(), this.getHeight());
        }
        else  if(RectangleAnchor.TYPE.SOUTH_EAST == anchor.getType()) {
            this.setSize(anchor.getX() - this.getX(), anchor.getY() - this.getY());
        }
        else if(RectangleAnchor.TYPE.SOUTH_MIDDLE == anchor.getType()) {
            this.setSize(this.getWidth(), anchor.getY() - this.getY());
        }
        else if(RectangleAnchor.TYPE.SOUTH_WEST == anchor.getType()) {
            Point mark = new Point(this.getX() + this.getWidth(), this.getY());
            this.setLocation(anchor.getX(), this.getY());
            this.setSize(((Double)mark.getX()).intValue() - anchor.getX(), anchor.getY() - ((Double)mark.getY()).intValue());
        }
        else  if(RectangleAnchor.TYPE.MIDDLE_WEST == anchor.getType()) {
            Point mark = new Point(this.getX() + this.getWidth(), this.getY() + this.getHeight() / 2);
            this.setLocation(anchor.getX(), this.getY());
            this.setSize(((Double)mark.getX()).intValue() - anchor.getX(), this.getHeight());
        }
        this.updateUI();
        this.refreshAnchors();
    }

    /**
     * Set the size of the current element
     * @param width The new width of the element
     * @param height The new height of the element
     */
    public void setSize(int width, int height) {
        super.setSize(width, height);
    }

    public void setLocation(int x, int y) {
        if(x < 0) x = 0;
        else if(y < 0) y = 0;
        else if(x + this.getWidth() > factoryPanel.getWidth()) x = factoryPanel.getWidth() - this.getWidth();
        else if(y + this.getHeight() > factoryPanel.getHeight()) y = factoryPanel.getHeight() - this.getHeight();
        super.setLocation(x,y);
    }

    /**
     * Reset the content of the current element
     */
    void editElement() {
        updateUI();
    }

    void deleteElement() {

        factoryPanel.deleteRectangleElement(this);
    }
}
