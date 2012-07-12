package view.query;

import controller.query.QueryBuilder;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: regisbelson
 * Date: 07/07/12
 * Time: 17:41
 * To change this template use File | Settings | File Templates.
 */
public class QFieldSelector extends JPanel implements ActionListener {
    private ArrayList<String> fields;
    private ArrayList<String> selectedFields;
    private ArrayList<JCheckBox> fieldCheckboxes;
    private JCheckBox selectAll;

    private QueryFrame parentFrame;
    private QSourceSelector selector;

    public QFieldSelector(QueryFrame frame, QSourceSelector _selector)
    {
        this.parentFrame = frame;
        this.selector = _selector;
        selectedFields = new ArrayList<String>();
        fieldCheckboxes = new ArrayList<JCheckBox>();
        this.setLayout(new MigLayout());
        selectAll = new JCheckBox("ALL FIELDS");
        selectAll.addActionListener(this);
        this.add(selectAll, "wrap");
    }

    public void setFields(ArrayList<String> fields) {
        this.fields = fields;
        this.selectedFields.clear();
        this.selectAll.setSelected(false);
        this.removeAllComponents();
        updateUI();
        this.addComponents();
    }

    private void removeAllComponents() {
        for(JCheckBox checkBox : fieldCheckboxes)
            remove(checkBox);
        this.fieldCheckboxes.clear();
    }

    private void addComponents() {

        for(String field : fields) {
            JCheckBox currentCheckbox = new JCheckBox(field);
            currentCheckbox.addActionListener(this);
            fieldCheckboxes.add(currentCheckbox);
            this.add(currentCheckbox, "wrap");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JCheckBox source = (JCheckBox)e.getSource();
        if(source == this.selectAll) {
            for(JCheckBox checkbox : fieldCheckboxes) {
                checkbox.setSelected(source.isSelected());
                e.setSource(null);
            }
            this.actionPerformed(e);
        }
        else
        {
            this.selectedFields.clear();
            for(JCheckBox checkbox : fieldCheckboxes)
            {
                if(checkbox.isSelected()) this.selectedFields.add(checkbox.getText());
                else {
                    this.selectedFields.remove(checkbox.getText());
                    this.selectAll.setSelected(false);
                }
            }
            String query;
            if(selectedFields.isEmpty()) query = "";
            else query = QueryBuilder.build(selectedFields, selector.getSelectedTable());
            parentFrame.sqlEditor.setText(query);
        }
    }
}
