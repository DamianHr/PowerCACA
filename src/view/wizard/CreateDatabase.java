package view.wizard;

import model.diagram.DatabaseModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.regex.*;

/**
 * CreateDatabase is a wizard wich allow user
 * to create a database step by step.
 * This class extends the WizardWindow class.
 * @see WizardWindow
 * @author fconstant
 */
public class CreateDatabase extends WizardWindow {

    private JTextField tf;
    private String dbName;

    /**
     * This is the constructor of the CreateDatabase class.
     * It initialize the window of this wizard.
     * @see CreateDatabase
     * @param component This is the component that will be the owner of the WizardWindow (JDialog)
     */
    public CreateDatabase(JComponent component)
    {
        super(component);
        setTitle("Assistant de création de base de données");
    }

    /**
     * This method intitialize the content of the wizard,
     * the differents steps and launch the wizard.
     * @return Return an object as a result of the wizard
     */
    @Override
    public Object ShowDialog()
    {
        AddStep(createIntroduction());
        AddStep(createPanelStep1());
        AddStep(createPanelStepFinal());
        AddLeftContent(createLogo());

        return super.ShowDialog();
    }

    /**
     * This method create an introduction step for the wizard.
     * @return It returns the JPanel that contains the introduction
     */
    private JPanel createIntroduction()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("insets 10"));


        JLabel title = new JLabel("<html>Bienvenue dans l'assistant de création de base de données</html>");
        title.setFont(new Font("Serif", Font.BOLD, 22));
        title.setPreferredSize(new Dimension(400, 50));
        panel.add(title, "gapbottom 10, wrap");

        JLabel intro = new JLabel("<html>Cet assistant va vous guider dans la procédure de création" +
                " de base de données. Suivez les indications, parcourrez les étapes et vous " +
                "aurez créé votre base de données.</html>");
        intro.setPreferredSize(new Dimension(400, 300));
        intro.setVerticalAlignment(SwingConstants.TOP);
        panel.add(intro);

        return panel;
    }

    /**
     * This method create the first step after the introduction for the wizard.
     * This step allow user to choose the SGBD of his database and its name.
     * @return It returns the JPanel that contains the form
     */
    private  JPanel createPanelStep1()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        // Le GridBagConstraint fait en sorte que tout les éléments se suivent de
        // manière verticale automatiquement, sans s'en préoccuper
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(30, 30, 30, 30);

        panel.add(new JLabel("Système de base de données :"), gbc);
        JComboBox combo = new JComboBox();
        combo.addItem("MySQL");
        combo.setSelectedIndex(0);
        combo.setEnabled(false);
        panel.add(combo, gbc);
        panel.add(new JLabel("Nom de votre base de données :"), gbc);
        tf = new JTextField();
        panel.add(tf, gbc);
        return panel;
    }

    /**
     * This method create the last step for the wizard.
     * This step tell the user that the wizard is finish and invite the user
     * to click on the "Terminer" button.
     * @return It returns the JPanel that contains the final step
     */
    private  JPanel createPanelStepFinal()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("insets 10"));


        JLabel title = new JLabel("<html>Préparation à la création de base de données réussi</html>");
        title.setFont(new Font("Serif", Font.BOLD, 22));
        title.setPreferredSize(new Dimension(400, 50));
        panel.add(title, "gapbottom 10, wrap");

        JLabel end = new JLabel("<html>Vous avez terminé l'assistant de création de base " +
                "de données avec succès. Cliquez sur \"Terminer\" pour finaliser la création de" +
                " votre base de données.</html>");
        end.setPreferredSize(new Dimension(400, 300));
        end.setVerticalAlignment(SwingConstants.TOP);
        panel.add(end);

        return  panel;
    }

    /**
     * This method create the logo that figure on the left of the
     * wizard. It represent a database.
     * @return It returns the JComponent that displays the logo
     */
    private JComponent createLogo()
    {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(new ImageIcon("src/view/wizard/img/database.jpg"));
        label.setPreferredSize(new Dimension(167, 371));
        panel.add(label);

        return panel;
    }

    /**
     * This method is called when the user click the "Next step" button.
     * It gives the index of the current step. We can check if the form
     * of this step is well filled.
     * @param index Index of the current step
     * @return A boolean that indicates if the user can move to the next step, or not
     */
    @Override
    protected  boolean validateStep(int index)
    {
        if (index == 1) dbName = tf.getText();
        return index == 1 ? Pattern.matches("^[a-zA-Z0-9_]+$", tf.getText()) : true;
    }

    /**
     * This method is called when the user click on the
     * "Finish" button. In this method, we finalize the creation
     * of the DatabaseModel object and set this object as the
     * result of the wizard.
     * @see DatabaseModel
     */
    @Override
    protected void wizardEnding()
    {
        result = new DatabaseModel(dbName);
    }

    /**
     * This method is called when the user close the wizard window.
     * In this method, we cancel all things that the user done in
     * the wizard.
     */
    @Override
    protected void wizardClosing()
    {
        result = null;
    }
}