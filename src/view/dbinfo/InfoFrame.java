package view.dbinfo;

import library.db.DBConnector;
import library.db.DBInfo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.io.Console;
import java.text.ParseException;

/**
 * Created with IntelliJ IDEA.
 * User: regisbelson
 * Date: 11/07/12
 * Time: 12:46
 * To change this template use File | Settings | File Templates.
 */
public class InfoFrame {
    private static JTextField host;
    private static JTextField port;
    private static JTextField username;
    private static JPasswordField password;

    private static DBInfo dbInfo;
    private static JButton okBtn;
    private static JButton testBtn;
    private static Container content;
    private static JDialog dialog;

    public static DBInfo showDialog(JFrame parent)
    {
        return showDialog(parent, new DBInfo());
    }

    /**
     * Affiche une boite de dialogue permettant de saisir un texte et renvoie la valeur saisie.
     * @param parent La Frame parente, qui sera désactivée par ce dialog.
     * @param dbInfo L'objet DBInfo à remplir ou éditer.
     * @return L'objet DBInfo contenant les valeurs saisies par l'utilisateur.
     */
    public static DBInfo showDialog(JFrame parent, DBInfo dbInfo)
    {
        dialog = new JDialog(parent, true);
        InfoFrame.dbInfo = dbInfo;
        content = dialog.getContentPane();
        content.setLayout(new MigLayout("fillx"));

        dialog.setTitle("Informations de connexion à MySQL");
        dialog.setResizable(false);
        initializeComponents();
        dialog.pack();

        dialog.setBounds(((parent.getWidth() / 2) - (dialog.getWidth() / 2)), ((parent.getHeight() / 2) - (dialog.getHeight() / 2)), dialog.getWidth(), dialog.getHeight());
        dialog.setVisible(true);

        return dbInfo;

    }

    private static void initializeComponents()
    {
        //Hostname
        content.add(new JLabel("Nom d'hote (ou adresse IP) : "),"growx");
        content.add(host = new JTextField(), "wrap, growx,  w 100px!");

        //Port
        content.add(new JLabel("Port : "),"growx");
        content.add(port = new JTextField("3306"), "wrap, growx");

        //Username
        content.add(new JLabel("Nom d'utilisateur : "),"growx");
        content.add(username = new JTextField(), "growx, wrap");

        //Password
        content.add(new JLabel("Mot de passe : "),"growx");
        content.add(password = new JPasswordField(), "wrap, growx");

        //Test button
        testBtn = new JButton("Tester");
        testBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //checkFields();
                testConnection();
            }
        });
        content.add(testBtn, "align center");

        //OK Button
        okBtn = new JButton("OK");
        okBtn.setEnabled(false);
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispatchEvent(new WindowEvent(
                        dialog, WindowEvent.WINDOW_CLOSING
                ));
            }
        });
        content.add(okBtn);
    }

    private static void testConnection() {
        DBConnector db = new DBConnector(username.getText(), new String(password.getPassword()), host.getText(), Integer.parseInt(port.getText()));
        db.connect();
        if(db.isConnected()) {
            JOptionPane.showMessageDialog(dialog,"Connexion réussie");
            okBtn.setEnabled(true);
            dbInfo.setHostname(host.getText());
            dbInfo.setPassword(new String(password.getPassword()));
            dbInfo.setPort(Integer.parseInt(port.getText()));
            dbInfo.setUsername(username.getText());
        }

    }
}
