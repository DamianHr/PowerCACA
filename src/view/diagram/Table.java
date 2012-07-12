package view.diagram;

import net.miginfocom.swing.MigLayout;
import view.diagram.Anchor.RectangleAnchor;
import view.wizard.CreateTable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Table extends RectangleElement {

    /**
     *
     */
    final private Dimension BIGSIZE = new Dimension(130, 180);
    final private Dimension SMALLSIZE = new Dimension(130, 40);
    private Dimension minSize = new Dimension(130, 32);
    private static final long serialVersionUID = 1L;
    private Point defaultPos = new Point(0,0);
    private Dimension defaultDim = BIGSIZE;
    private Color defaultColor = Color.WHITE;

    private String nameModel;

    public Table(FactoryPanel factoryPanel) {
        this(factoryPanel, new Point(0, 0));
    }

    public Table(FactoryPanel factoryPanel, Point pos) {
        this(factoryPanel, pos, new Dimension(130, 180));
    }

    public Table(FactoryPanel factoryPanel, Point pos, Dimension dim) {
        this(factoryPanel, pos, dim, new Color(244, 253,255));
    }

    public Table(FactoryPanel factoryPanel, Point pos, Dimension dim, Color col) {
        super(factoryPanel);
        this.setLocation(pos);
        this.setSize(dim);
        this.setBackground(col);
        this.setLayout(new MigLayout("fill, inset 0, debug, top"));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        setAnchors(new ArrayList<RectangleAnchor>());
        this.initAnchors();
        this.refreshAnchors();

        CreateTable createTable = new CreateTable(this.factoryPanel.getFrame(), FactoryPanel.databaseModel);
        nameModel = (String)createTable.ShowDialog();
        //fillContent(nameModel); <-- Utiliser celle ci !
        fillContent();
    }

    public void setSize(int width, int height) {
        if(width < minSize.getWidth())
            width = ((Double)minSize.getWidth()).intValue();
        if(height < minSize.getHeight())
            height = ((Double)minSize.getHeight()).intValue();
        super.setSize(width, height);
    }

    public void editElement() {
        //UpdateTable updateTable = new UpdateTable(this.frame, FactoryPanel.databaseModel.getTables().get(nameModel));
        //Boolean updateDone = updateTable.ShowDialog();
        //if(updateDone)
            fillContent();
        updateUI();
    }

    protected void deleteElement() {
        //Suppression du model

        super.deleteElement();
    }
    //private void fillContent(String tableName) {
    private void fillContent() {
        //Title
        JLabel l =new JLabel("Hello");
        l.setOpaque(true);
        l.setBackground(new Color(234,234,234));
        l.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));

        l.setHorizontalAlignment(JLabel.CENTER);
        this.add(l, "dock north, h 30px!");
        //this.add(new JLabel(modelTable.getName()), "wrap, grow");

        //Fields
        for(int i = 0; i < 3; i++){
            JLabel label = new JLabel(" Field + " + i );
            label.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));
            this.add(label, "dock north, h 15px!");
        }
        updateUI();
    }
}