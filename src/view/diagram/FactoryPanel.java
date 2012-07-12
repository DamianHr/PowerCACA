package view.diagram;

import model.diagram.DatabaseModel;
import net.miginfocom.swing.MigLayout;
import org.json.simple.JSONObject;
import view.diagram.anchor.LinkAnchor;
import view.diagram.anchor.RectangleAnchor;
import view.diagram.toolbox.ToolboxPanel;
import view.wizard.CreateDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
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
        this.setLayout(new MigLayout("fill, insets 0"));
        this.pane = new JLayeredPane();
        this.pane.setLocation(0,0);
        this.pane.setPreferredSize(frame.getSize());
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

        this.add(pane, "grow");
    }

    public void createDatabaseModel() {
        this.updateUI();
        CreateDatabase createDatabase = new CreateDatabase(this);
        databaseModel = (DatabaseModel)createDatabase.ShowDialog();
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
                Table newTable = createTable(position, null);
                this.rectangleElements.add(newTable);
                this.setCurrentRectangleElement(newTable);
                bringElementToFront(newTable);
            }
            break;
            case ANNOTATION: {
                Annotation newAnno = createComment(position, null);
                this.rectangleElements.add(newAnno);
                this.setCurrentRectangleElement(newAnno);
                bringElementToFront(newAnno);
                break;
            }
        }
    }

    private Table createTable(Point position, Dimension dimension) {
        Table table;
        if(dimension == null)
            table = new Table(this, position);
        else
            table = new Table(this, position, dimension);
        pane.add(table);
        for(RectangleAnchor anchor : table.getAnchors()) {
            pane.add(anchor);
        }
        return table;
    }

    private Annotation createComment(Point position, Dimension dimension) {
        Annotation annotation;
        if(dimension == null)
            annotation = new Annotation(this, position);
        else
            annotation = new Annotation(this, position, dimension);
        this.pane.add(annotation);
        for(RectangleAnchor anchor : annotation.getAnchors()) {
            pane.add(anchor);
        }
        return annotation;
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

   /* private void drawBackground(Graphics2D g2d) {
        g2d.setColor(Color.GRAY);
        for(int x = 0; x < this.getSize().width; x+=30){
            for(int y = 0; y < this.getSize().height; y+=30){
                Shape point = new Line2D.Float(new Point(x, y), new Point(x, y));
                g2d.draw(point);
            }
        }
    }*/

    private JSONObject exportJSON() {
        JSONObject jsonObject = new JSONObject();
        //jsonObject.put("database", DatabaseModel.)
        int i = 0;
        for(RectangleElement rectangleElement : rectangleElements) {
            JSONObject jsonRectangleElement = new JSONObject();
            jsonRectangleElement.put("x", rectangleElement.getX());
            jsonRectangleElement.put("y", rectangleElement.getX());
            jsonRectangleElement.put("width", rectangleElement.getWidth());
            jsonRectangleElement.put("height", rectangleElement.getHeight());
            if(rectangleElement instanceof Table) {
                jsonRectangleElement.put("nameModel", ((Table)rectangleElement).getNameModel());
            }
            else { // Annotation
                jsonRectangleElement.put("text", ((Annotation)rectangleElement).getText());
            }
            jsonObject.put("rect"+i, jsonRectangleElement);
            i++;
        }
        return jsonObject;
    }

    private void importJSON(JSONObject jsonObject) {
        //databaseModel. = jsonObject.get("database");
        int i = 0;
        for(Object object : jsonObject.keySet()) {
            if(((String)object).startsWith("rect")) {
                JSONObject jsonRectangleElement = ((JSONObject)jsonObject.get(object));
                Point point = new Point((Integer)jsonRectangleElement.get("x"), (Integer)jsonRectangleElement.get("y"));
                Dimension dimension = new Dimension((Integer)jsonRectangleElement.get("width"), (Integer)jsonRectangleElement.get("height"));
                if(jsonRectangleElement.get("nameModel") != null){
                    Table table = createTable(point, dimension);
                    table.setNameModel((String)jsonRectangleElement.get("nameModel"));
                }
                else {
                    Annotation annotation = createComment(point, dimension);
                    annotation.setText((String) jsonRectangleElement.get("text"));
                }
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
