package view.diagram;

import net.miginfocom.swing.MigLayout;
import view.diagram.Anchor.RectangleAnchor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * This class allows the management of comments created in the diagram
 * @author dhercun
 */

public class Annotation extends RectangleElement{

    final private Dimension SMALLSIZE = new Dimension(80,40);
    private Dimension minSize = SMALLSIZE;
    private static final long serialVersionUID = 1L;

    private JTextArea textContainer;

    /**
     * Constructor of a new DiagramElement, used to Annotation, a default Dimension is set
     * @param factoryPanel the factory of the element
     * @param pos the position of the element
     */
    public Annotation(FactoryPanel factoryPanel, Point pos) {
        this(factoryPanel, pos, new Dimension(100 , 80));
    }

    /**
     * Constructor of a new DiagramElement, used to Annotation, a default Dimension is set
     * @param factoryPanel the factory of the element
     * @param pos the position of the element
     * @param dim the dimension of the element
     */
    public Annotation(FactoryPanel factoryPanel, Point pos, Dimension dim) {
        this(factoryPanel, pos, dim, new Color(255, 253, 186));
    }

    /**
     * Constructor of a new DiagramElement, used to Annotation, a default Dimension is set
     * @param factoryPanel the factory of the element
     * @param pos the position of the element
     * @param dim the dimension of the element
     * @param col the background color of the element
     * @see FactoryPanel
     */
    public Annotation(FactoryPanel factoryPanel, Point pos, Dimension dim, Color col) {
        super(factoryPanel);
        this.setLocation(pos);
        this.setSize(dim);
        this.setBackground(col);
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        this.setLayout(new MigLayout());

        this.setAnchors(new ArrayList<RectangleAnchor>());
        this.initAnchors();
        this.refreshAnchors();

        this.textContainer = new JTextArea("");
        this.textContainer.setEditable(false);
        this.textContainer.setFont(new Font(Font.DIALOG, Font.ITALIC, 14));
        this.textContainer.setBackground(this.getBackground());
        this.add(this.textContainer, "grow, align center");

        ArrayList<String> str = new ArrayList<String>();
    }

    /**
     * Check the minimal size of the element et set it
     * @param width the width of the element
     * @param height the height of the element
     */
    public void setSize(int width, int height) {
        if(width < minSize.getWidth())
            width = ((Double)minSize.getWidth()).intValue();
        if(height < minSize.getHeight())
            height = ((Double)minSize.getHeight()).intValue();
        super.setSize(width, height);
    }

    /**
     * Call the Annotation edition frame and set the element content
     */
    public void editElement() {
        String newAnnon = AnnotationEditor.showDialog(this.factoryPanel.getFrame(), this.textContainer.getText());
        this.textContainer.setText(newAnnon);
    }

    /**
     * Set the element content
     * @param newString the new String of the element
     */
    private void fillContent(String newString) {
        this.textContainer.setText(newString);
        updateUI();
    }
}
