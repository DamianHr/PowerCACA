package view.query;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class QSourcePanel extends JPanel {
    private QSourceSelector selectorPanel;
    public QFieldSelector fieldsPanel;
    QueryFrame parentFrame;

    public QSourcePanel(QueryFrame frame)
    {
        this.parentFrame = frame;
        this.setLayout(new BorderLayout());
        this.addComponents();
        this.setBackground(Color.CYAN);
        this.setVisible(true);
    }

    private void addComponents() {
        selectorPanel = new QSourceSelector(this.parentFrame);
        fieldsPanel = new QFieldSelector(this.parentFrame, selectorPanel);
        this.add(selectorPanel, BorderLayout.NORTH);
        selectorPanel.setDbList(parentFrame.db.getDatabaseList());
        selectorPanel.setTableList(parentFrame.db.getTableFields(selectorPanel.getSelectedDatabase()));
        selectorPanel.initialized = true;
        this.add(fieldsPanel, BorderLayout.CENTER);
        fieldsPanel.setFields(parentFrame.db.getTableFields(selectorPanel.getSelectedTable()));
    }
}
