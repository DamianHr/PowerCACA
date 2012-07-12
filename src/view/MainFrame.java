package view;

import view.diagram.FactoryPanel;

import javax.swing.*;

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
        FactoryPanel factoryPanel = new FactoryPanel(this);
        this.add(factoryPanel);
        this.pack();
		this.setVisible(true);
        /**
        *Démarrage Message bienvenu
        */
        //WelcomeFrame welcomeFrame = new WelcomeFrame(this);
	}

    public void initialize() {
        this.add(new FactoryPanel(this));
    }

    public void initialize(String JSonFilePath) {
        this.add(new FactoryPanel(this, JSonFilePath));
    }
}
