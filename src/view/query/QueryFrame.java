package view.query;


import library.db.DBConnector;
import library.db.DBInfo;
import view.dbinfo.InfoFrame;
import view.diagram.AnnotationEditor;

import javax.swing.*;
import java.util.ArrayList;

public class QueryFrame extends JFrame{

    QResultTable resultTable;
    QSourcePanel tableTree;
    QSQLEditor sqlEditor;
    QSourcePanel sourcePanel;
    public DBConnector db;
    private  DBInfo dbInfo;
    private static final long serialVersionUID = 1L;

    public QueryFrame()
    {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                launch();
            }
        };
        SwingUtilities.invokeLater(r);
    }

    private void launch()
    {
        this.setSize(800, 600);
        this.setTitle("Requêtes");
        if(dbInfo == null)
            dbInfo = InfoFrame.showDialog(this);
        db = new DBConnector(dbInfo.getUsername(),dbInfo.getPassword(), dbInfo.getHostname(), dbInfo.getPort());
        db.connect();

        resultTable = new QResultTable(this);
        tableTree = new QSourcePanel(this);

        JSplitPane hSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        this.add(hSplit);

        //hSplit.setLayout(new BorderLayout());
        sourcePanel = new QSourcePanel(this);
        hSplit.setLeftComponent(sourcePanel);


        JSplitPane vSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        hSplit.setRightComponent(vSplit);

        sqlEditor = new QSQLEditor(this);
        vSplit.setLeftComponent(sqlEditor);

        JScrollPane resultTableSPane = new JScrollPane(resultTable);
        vSplit.setRightComponent(resultTableSPane);

        this.setVisible(true);

        /*ArrayList<String> columnList = new ArrayList<String>();
        columnList.add("Nom");
        columnList.add("Prénom");
        columnList.add("Age");
        resultTable.setColumns(columnList);*/

        ArrayList<String> rows = new ArrayList<String>();
    }
}