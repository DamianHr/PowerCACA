package view.welcome;

import net.miginfocom.swing.MigLayout;
import view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: asus
 * Date: 11/07/12
 * Time: 12:37
 * To change this template use File | Settings | File Templates.
 */
public class WelcomeFrame extends JDialog {
    private MainFrame frame;
    public WelcomeFrame(MainFrame parentFrame) {
        super(JOptionPane.getFrameForComponent(parentFrame), true);
        this.frame = parentFrame;
        this.setTitle("Welcome !");
        this.setBounds(frame.getWidth() / 2, frame.getHeight() / 2, 100, 80);
        this.setLayout(new MigLayout());
        JButton newDiagramButton = new JButton("Nouveau modèle de base de données");
        newDiagramButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.initialize();
                dispatchEvent(new WindowEvent(
                        WelcomeFrame.this, WindowEvent.WINDOW_CLOSING
                ));
            }
        });
        this.add(newDiagramButton, "align center,wrap");
        JButton openDiagramButton = new JButton("Ouvrir un modèle de base de données existant");
        openDiagramButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnVal = fileChooser.showOpenDialog(WelcomeFrame.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    frame.initialize(file.getPath());
                    dispatchEvent(new WindowEvent(
                            WelcomeFrame.this, WindowEvent.WINDOW_CLOSING
                    ));
                }
            }
        });
        this.add(openDiagramButton, "align center");

        this.pack();
        this.setVisible(true);

    }
}
