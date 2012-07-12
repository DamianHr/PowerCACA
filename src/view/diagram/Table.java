package view.diagram;

import net.miginfocom.swing.MigLayout;
import org.json.simple.JSONObject;
import view.diagram.anchor.RectangleAnchor;
import view.wizard.CreateTable;
import view.wizard.UpdateTable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Table extends RectangleElement {

    /**
     *
     */
    private Dimension minSize = new Dimension(130, 32);
    private static final long serialVersionUID = 1L;

    private String nameModel;
    public String getNameModel() {
        return this.nameModel;
    }
    public void setNameModel(String nameModel) {
        this.nameModel = nameModel;
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

        CreateTable createTable = new CreateTable(this.factoryPanel, FactoryPanel.databaseModel);
        nameModel = (String)createTable.ShowDialog();
        if(FactoryPanel.databaseModel.getTables().get(nameModel)!=null)
            fillContent(FactoryPanel.databaseModel.getTables().get(nameModel));
    }

    public void setSize(int width, int height) {
        if(width < minSize.getWidth())
            width = ((Double)minSize.getWidth()).intValue();
        if(height < minSize.getHeight())
            height = ((Double)minSize.getHeight()).intValue();
        super.setSize(width, height);
    }

    public void setLocation(int x, int y) {
        if(x < 0) x = 0;
        else if(y < 0) y = 0;
        else if(x + this.getWidth() > factoryPanel.getWidth()) x = factoryPanel.getWidth() - this.getWidth();
        else if(y + this.getHeight() > factoryPanel.getHeight()) y = factoryPanel.getHeight() - this.getHeight();
        super.setLocation(x, y);
    }

    public void editElement() {
        UpdateTable updateTable = new UpdateTable(this, FactoryPanel.databaseModel.getTables().get(this.nameModel));
        Boolean updateDone = (Boolean)updateTable.ShowDialog();
        if(updateDone)
            fillContent(FactoryPanel.databaseModel.getTables().get(this.nameModel));
    }

    protected void deleteElement() {
        factoryPanel.databaseModel.removeTable(this.nameModel);
        super.deleteElement();
    }

    private void fillContent(model.diagram.Table table) {
        //Title
        this.removeAll();
        JLabel l =new JLabel(table.getName());
        l.setOpaque(true);
        l.setBackground(new Color(234,234,234));
        l.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));

        l.setHorizontalAlignment(JLabel.CENTER);
        this.add(l, "dock north, h 30px!");

        //Fields
        for(String field : table.getFieldNames()){
            JLabel label = new JLabel(field + " " + table.getField(field).getFieldType());
            label.setFont(new Font(Font.DIALOG, Font.PLAIN, 10));
            this.add(label, "dock north, h 15px!");
        }
        updateUI();
    }
}