package view;

import view.wizard.CreateDatabase;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuBar extends JMenuBar {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4352341802466235417L;

	public MainMenuBar()
	{
		setMenuFile();
		setMenuEdit();
        setMenuWizard();
		setMenuHelp();
	}
	
	private void setMenuFile()
	{
		JMenu menuFile = new JMenu("Fichier");
		this.add(menuFile);
		JMenuItem menuItemNew = new JMenuItem("Nouveau");
        menuItemNew.setName("ItemNew");
        menuItemNew.addActionListener(new MenuActionListener());
		menuFile.add(menuItemNew);
		JMenuItem menuItemOpen = new JMenuItem("Ouvrir");
        menuItemOpen.setName("ItemOpen");
        menuItemOpen.addActionListener(new MenuActionListener());
        menuFile.add(menuItemOpen);
		JMenuItem menuItemSave = new JMenuItem("Enregistrer");
        menuItemSave.setName("ItemSave");
        menuItemSave.addActionListener(new MenuActionListener());
		menuFile.add(menuItemSave);
		JMenuItem menuItemSaveAs = new JMenuItem("Enregistrer sous");
        menuItemSaveAs.setName("ItemSaveAs");
        menuItemSaveAs.addActionListener(new MenuActionListener());
		menuFile.add(menuItemSaveAs);
		JMenuItem menuItemExit = new JMenuItem("Quitter");
        menuItemExit.setName("ItemExit");
        menuItemExit.addActionListener(new MenuActionListener());
		menuFile.add(menuItemExit);
	}
	private void setMenuEdit()
	{
		JMenu menuEdit = new JMenu("Edition");
		this.add(menuEdit);
		JMenuItem menuItemCopy = new JMenuItem("Copier");
        menuItemCopy.setName("ItemCopy");
        menuItemCopy.addActionListener(new MenuActionListener());
		menuEdit.add(menuItemCopy);
		JMenuItem menuItemCut = new JMenuItem("Couper");
        menuItemCut.setName("ItemCut");
        menuItemCut.addActionListener(new MenuActionListener());
		menuEdit.add(menuItemCut);
		JMenuItem menuItemPast = new JMenuItem("Coller");
        menuItemPast.setName("ItemPast");
        menuItemPast.addActionListener(new MenuActionListener());
		menuEdit.add(menuItemPast);
	}
	private void setMenuHelp()
	{
		JMenu menuHelp = new JMenu("Aide");
		this.add(menuHelp);
		
		JMenuItem menuItemAbout = new JMenuItem("A propos");
        menuItemAbout.setName("ItemAbout");
        menuItemAbout.addActionListener(new MenuActionListener());
		menuHelp.add(menuItemAbout);
		
		JMenuItem menuItemHelp = new JMenuItem("Aide");
        menuItemHelp.setName("ItemHelp");
        menuItemHelp.addActionListener(new MenuActionListener());
		menuHelp.add(menuItemHelp);
	}
    private void setMenuWizard()
    {
        JMenu menuWizard = new JMenu("Assistant");
        this.add(menuWizard);
        JMenuItem menuDatabase = new JMenuItem("Nouvelle base de données");
        menuDatabase.setName("createDatabase");
        menuDatabase.addActionListener(new MenuActionListener());
        menuWizard.add(menuDatabase);
        JMenuItem menuTable = new JMenuItem("Nouvelle table");
        menuTable.setName("createTable");
        menuTable.addActionListener(new MenuActionListener());
        menuWizard.add(menuTable);
    }
}
