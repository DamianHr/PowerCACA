package view.diagram;

import model.diagram.DatabaseModel;
import view.diagram.Anchor.LinkAnchor;
import view.diagram.Anchor.RectangleAnchor;
import view.diagram.Toolbox.ToolboxPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 *
 */
public class FactoryPanel extends JPanel{

    public enum TOOL {
        NONE(0), ANNOTATION(1), LINK(2), TABLE(3);
        private final int value;
        private TOOL(int value) {this.value = value;}
        public int getValue() {return this.value;}
    };

    static public TOOL activeTOOL = TOOL.NONE;

    private JFrame frame;
    public JFrame getFrame() {
        return frame;
    }

    static public JLayeredPane pane;

    static public LinkAnchor currentLinkAnchor;
    static public RectangleElement currentRectangleElement;

    static  public DatabaseModel databaseModel;

    public ArrayList<RectangleElement> rectangleElements = new ArrayList<RectangleElement>();

    ArrayList<Link> links = new ArrayList<Link>();
    public ArrayList<Link> getLinks() {
        return this.links;
    }

    public FactoryPanel(JFrame frame, String JSonFilePath) {
        this(frame);

    }

    public FactoryPanel(JFrame frame) {
        this.frame = frame;
        this.setBackground(Color.WHITE);
        this.pane = new JLayeredPane();
        this.pane.setPreferredSize(new Dimension(800,600));
        this.pane.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                createElement(e.getPoint());
            }
            public void mouseReleased(java.awt.event.MouseEvent arg0) {
            }
        });

        ToolboxPanel toolboxPanel = new ToolboxPanel(new Point(10, 10));
        toolboxPanel.addButton("Table", TOOL.TABLE);
        toolboxPanel.addButton("Link", TOOL.LINK);
        toolboxPanel.addButton("Annotation", TOOL.ANNOTATION);
        this.pane.add(toolboxPanel, 10000);

        this.add(pane);
    }

    private void createElement(Point position) {
        switch (FactoryPanel.activeTOOL) {
            /*case LINK: {
                Link link = createLink(position, position);
                getLinks().add(link);
                //addLinkAnchors(link.getFirstAnchor(), link.getSecondAnchor());
            }
            break;*/
            case TABLE: {
                Table newTable = createTable(position);
                this.rectangleElements.add(newTable);
                this.setCurrentRectangleElement(newTable);
                bringElementToFront(newTable);
            }
            break;
            case ANNOTATION: {
                Annotation newAnno = createComment(position);
                this.rectangleElements.add(newAnno);
                this.setCurrentRectangleElement(newAnno);
                bringElementToFront(newAnno);
                break;
            }
        }
    }

    private Table createTable(Point position) {
        Table table = new Table(this, position);
        pane.add(table);
        for(RectangleAnchor anchor : table.getAnchors()) {
            pane.add(anchor);
        }
        return table;
    }

    private Annotation createComment(Point position) {
        Annotation anno = new Annotation(this, position);
        this.pane.add(anno);
        for(RectangleAnchor anchor : anno.getAnchors()) {
            pane.add(anchor);
        }
        return anno;
    }

    public void deleteRectangleElement(RectangleElement element) {
        rectangleElements.remove(element);
        deleteRectangleAnchors(element.getAnchors());
        pane.remove(element);
        this.updateUI();
    }

    public void deleteRectangleAnchors(ArrayList<RectangleAnchor> anchors) {
        for(RectangleAnchor anchor : anchors)
            pane.remove(anchor);
    }

    public Link createLink(Point p1, Point p2) {
        LinkAnchor a1 = new LinkAnchor(p1, true);
        LinkAnchor a2 = new LinkAnchor(p2, false);
        Link link = new Link(a1, a2);

        return link;
    }


    public static void setCurrentRectangleElement(RectangleElement element) {
        if(currentRectangleElement !=null)
            currentRectangleElement.setAnchorsVisibility(false);
        currentRectangleElement = element;
        currentRectangleElement.setAnchorsVisibility(true);
    }

    public static void bringElementToFront(RectangleElement element) {
        int newIndex = 1;
        pane.setComponentZOrder(element, newIndex);
        for(RectangleAnchor anchor : element.getAnchors())
            pane.setComponentZOrder(anchor, ++newIndex);
    }

/*    MouseMotionAdapter linkMMA = new MouseMotionAdapter() {
        public void mouseDragged(MouseEvent e) {
            FactoryPanel.this.paint(FactoryPanel.this.getGraphics());
        }
    };*/

    private void drawBackground(Graphics2D g2d) {
        g2d.setColor(Color.GRAY);
        for(int x = 0; x < this.getSize().width; x+=30){
            for(int y = 0; y < this.getSize().height; y+=30){
                Shape point = new Line2D.Float(new Point(x, y), new Point(x, y));
                g2d.draw(point);
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        //drawBackground(g2d);
        g2d.setColor(Color.BLACK);
        for (Link link : links) {
            LinkAnchor a1 = link.getFirstAnchor(), a2 = link.getSecondAnchor();
            Shape linkShape = new Line2D.Float(a1.getCenter(), a2.getCenter());
            g2d.setStroke(new BasicStroke(1));
            g2d.draw(linkShape);
        }
        g.dispose();
    }
}
