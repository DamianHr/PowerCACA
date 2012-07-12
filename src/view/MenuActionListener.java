package view;

import com.sun.xml.internal.bind.v2.TODO;
import model.diagram.DatabaseModel;
import view.wizard.CreateDatabase;
import view.wizard.CreateTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Florian
 * Date: 05/07/12
 * Time: 23:29
 * To change this template use File | Settings | File Templates.
 */
public class MenuActionListener implements ActionListener {

    static DatabaseModel dbm;

    @Override
    public void actionPerformed(ActionEvent e) {
        // Clique sur menu Fichier > nouveau
        if (((JMenuItem)e.getSource()).getName().equalsIgnoreCase("ItemNew"))
        {
            // TODO
        }
        // Clique sur menu Fichier > Ouvrir
        else if (((JMenuItem)e.getSource()).getName().equalsIgnoreCase("ItemOpen"))
        {
            // TODO
        }
        // Clique sur menu Fichier > Enregistrer
        else if (((JMenuItem)e.getSource()).getName().equalsIgnoreCase("ItemSave"))
        {
            // TODO
        }
        // Clique sur menu Fichier > Enregistrer Sous
        else if (((JMenuItem)e.getSource()).getName().equalsIgnoreCase("ItemSaveAs"))
        {
            // TODO
        }
        // Clique sur menu Fichier > Quitter
        else if (((JMenuItem)e.getSource()).getName().equalsIgnoreCase("ItemExit"))
        {
            // TODO
        }
        // Clique sur menu Edition > Copier
        else if (((JMenuItem)e.getSource()).getName().equalsIgnoreCase("ItemCopy"))
        {
            // TODO
        }
        // Clique sur menu Edition > Couper
        else if (((JMenuItem)e.getSource()).getName().equalsIgnoreCase("ItemCut"))
        {
            // TODO
        }
        // Clique sur menu Edition > Coller
        else if (((JMenuItem)e.getSource()).getName().equalsIgnoreCase("ItemPaste"))
        {
            // TODO
        }
        // Clique sur menu Aide > A Propos
        else if (((JMenuItem)e.getSource()).getName().equalsIgnoreCase("ItemAbout"))
        {
            // TODO
        }
        // Clique sur menu Aide > Aide
        else if (((JMenuItem)e.getSource()).getName().equalsIgnoreCase("ItemHelp"))
        {
            // TODO
        }
        // Clique sur menu Wizards > Création database
        else if (((JMenuItem)e.getSource()).getName().equalsIgnoreCase("createDatabase"))
        {
            CreateDatabase cd = new CreateDatabase((JMenuItem)e.getSource());
            dbm = (DatabaseModel)cd.ShowDialog();
        }
        // Clique sur menu Wizard > Création table
        else if (((JMenuItem)e.getSource()).getName().equalsIgnoreCase("createTable"))
        {
            // TODO : Envoyer le DatabaseModel de Damian
            CreateTable ct = new CreateTable((JMenuItem)e.getSource(), dbm);
            ct.ShowDialog();
            int i = 0;
        }
    }
}
