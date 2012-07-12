package view.query;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class QResultTable extends JTable {

    private QueryFrame parentFrame;
    DefaultTableModel model;
    public QResultTable(QueryFrame frame)
    {
        this.parentFrame = frame;
    }

    public void setColumns(ArrayList<String> columns)
    {
        model = new DefaultTableModel();
        for(String column : columns) model.addColumn(column);
        this.setModel(model);

    }

    public void addRow(ArrayList<Object> row)
    {
        this.model.addRow(row.toArray());
    }

}
