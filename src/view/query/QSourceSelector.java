package view.query;

import net.miginfocom.swing.MigLayout;
import sun.swing.text.html.FrameEditorPaneTag;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: regisbelson
 * Date: 07/07/12
 * Time: 17:42
 * To change this template use File | Settings | File Templates.
 */
public class QSourceSelector extends JPanel implements ItemListener {
    private JComboBox dbList;
    private JComboBox tableList;
    private QueryFrame parentFrame;
    public boolean initialized = false, changeInProgress = false;

    public QSourceSelector(QueryFrame frame)
    {
        this.parentFrame = frame;
        this.setLayout(new MigLayout());
        this.addComponents();
    }

    private void addComponents() {

        JLabel dbLabel = new JLabel("Database");
        this.add(dbLabel);

        tableList = new JComboBox();
        tableList.addItemListener(this);

        dbList = new JComboBox();
        dbList.addItemListener(this);

        JLabel tableLabel = new JLabel("Table");

        this.add(dbList,"wrap");

        this.add(tableLabel);
        this.add(tableList);



    }

    public void setDbList(ArrayList<String> newDBList)
    {
        this.dbList.removeAllItems();
        for (String db : newDBList) this.dbList.addItem(db);
    }

    public void setTableList(ArrayList<String> newTableList)
    {
        this.tableList.removeAllItems();
        for (String table : newTableList) this.tableList.addItem(table);
    }

    public String getSelectedTable()
    {
        return tableList.getSelectedItem() != null ? tableList.getSelectedItem().toString() : null;
    }

    public String getSelectedDatabase()
    {
        return dbList.getSelectedItem().toString();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(this.initialized && !changeInProgress) {
            if (e.getStateChange() == ItemEvent.SELECTED ) {
                JComboBox source = (JComboBox)e.getSource();
                if(source == dbList)
                {
                    String database = source.getSelectedItem().toString();
                    this.changeInProgress = true;
                    setTableList(parentFrame.db.getTableList(database));
                    this.changeInProgress = false;
                }
                else if(source == tableList)
                {
                    parentFrame.sourcePanel.fieldsPanel.setFields(parentFrame.db.getTableFields(tableList.getSelectedItem().toString()));
                    updateUI();
                }
            }
        }
    }
}
