package view.diagram;

import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created with IntelliJ IDEA.
 * User: regisbelson
 * Date: 09/07/12
 * Time: 17:32
 * To change this template use File | Settings | File Templates.
 */
public abstract class AnnotationEditor {

    private static JTextArea annotationText;
    private static JButton okBtn;
    private static Container content;
    private static JDialog dialog;

    public static String showDialog(JFrame parent)
    {
        return showDialog(parent, "");
    }

    /**
     * Affiche une boite de dialogue permettant de saisir un texte et renvoie la valeur saisie.
     * @param parent La Frame parente, qui sera désactivée par ce dialog.
     * @param currentAnnotation Le texte à afficher dans le champ de saisie.
     * @return La valeur saisie dans le champ de texte.
     */
    public static String showDialog(JFrame parent,  String currentAnnotation)
    {
        dialog = new JDialog(parent, true);
        content = dialog.getContentPane();
        content.setLayout(new MigLayout());

        dialog.setTitle("Commentaire");
        dialog.setResizable(false);
        initializeComponents();
        annotationText.setText(currentAnnotation);
        dialog.pack();

        dialog.setBounds(((parent.getWidth() / 2) - (dialog.getWidth()/2)), ((parent.getHeight() / 2) - (dialog.getHeight()/2)), dialog.getWidth(), dialog.getHeight() );
        dialog.setVisible(true);
        return annotationText.getText();

    }

    private static void initializeComponents()
    {
        content.add(new JLabel("Entrez votre commentaire : "),"wrap, grow");
        annotationText = new JTextArea();
        content.add(annotationText, "wrap,h 30px!, w 200px!, grow");
        okBtn = new JButton("OK");
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispatchEvent(new WindowEvent(
                        dialog, WindowEvent.WINDOW_CLOSING
                ));
            }
        });
        content.add(okBtn, "align center");
    }
}
