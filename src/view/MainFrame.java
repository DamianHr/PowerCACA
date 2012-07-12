package view;

import net.miginfocom.swing.MigLayout;
import view.diagram.FactoryPanel;
import view.welcome.WelcomeFrame;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MainFrame()
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
		this.setTitle("Power DataBase");
        /**
         * Faire la demande de sauvegarde avant fermeture!
         */
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		//invoke the components of the window
        this.setJMenuBar(new MainMenuBar());
        this.setPreferredSize(new Dimension(900, 650));
        this.pack();
		this.setVisible(true);
        /**
        *Démarrage Message bienvenue
        */
        WelcomeFrame welcomeFrame = new WelcomeFrame(this);
	}

    public void initialize() {
        FactoryPanel panel = new FactoryPanel(this);
        panel.setLocation(0, 0);
        this.getContentPane().setLayout(new MigLayout("fill, insets 0, debug"));
        this.add(panel, "grow");

        panel.createDatabaseModel();
    }

    public void initialize(String JSonFilePath) {
        FactoryPanel panel = new FactoryPanel(this, JSonFilePath);
        panel.setLocation(0, 0);
        this.add(panel);
        panel.createDatabaseModel();
    }
}
