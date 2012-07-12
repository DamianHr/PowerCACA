package view.query;

import library.db.SQLResult;
import jsyntaxpane.DefaultSyntaxKit;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class QSQLEditor extends JPanel implements ActionListener {
    private JEditorPane sqlEditor;
    private JScrollPane sqlEditorSP;
    private QueryFrame parentFrame;
    private JButton submitButton;

    public QSQLEditor(QueryFrame frame)
    {
        this.parentFrame = frame;
        this.setLayout(new MigLayout("fill"));

        DefaultSyntaxKit.initKit();
        sqlEditor = new JEditorPane();
        sqlEditorSP = new JScrollPane(sqlEditor);
        this.add(sqlEditorSP, "wrap, grow");

        sqlEditor.setContentType("text/sql");
        sqlEditor.setSize(new Dimension(100, 100));

        submitButton = new JButton("Exécuter");
        submitButton.addActionListener(this);
        this.add(submitButton, "span, align right");
    }



    public void setText(String text)
    {
        this.sqlEditor.setText(text);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String query = (sqlEditor.getSelectedText() !=  null) ? sqlEditor.getSelectedText() : sqlEditor.getText();

        SQLResult result = parentFrame.db.read(query);
        parentFrame.resultTable.setColumns(result.fields);
        for(ArrayList<String> record : result.data){
            ArrayList<Object> row = new ArrayList<Object>();
            for(String field : record) row.add(field);
            parentFrame.resultTable.addRow(row);
        }
    }
}
