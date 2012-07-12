package view.wizard;

import model.diagram.DatabaseModel;
import model.diagram.FieldType;
import model.diagram.Table;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * CreateTable is a wizard wich allow user
 * to create a table step by step.
 * This class extends the WizardWindow class.
 * @see WizardWindow
 * @author fconstant
 */
public class CreateTable extends WizardWindow {

    private HashMap componentMap;
    private JTextField tfTable;
    private JPanel table;
    private HashMap<String, FieldType> types = new HashMap<String, FieldType>();
    private JScrollPane jsp;
    private JPanel panel;
    private DatabaseModel model;

    private Color color1 = new Color(255, 255, 255);
    private Color color2 = new Color(217, 217, 217);

    private Integer rowCount = 0;
    private String nomTable;

    /**
     * This is the constructor of the CreateTable class.
     * It initialize the window of this wizard.
     * @param component This is the component that will be the owner of the WizardWindow (JDialog)
     * @param model This is the DatabaseModel that we will use in the wizard
     */
    public CreateTable(JComponent component, DatabaseModel model)
    {
        super(component);
        setTitle("Assistant de création de table");
        this.model = model;

        types.put("Texte", FieldType.VARCHAR);
        types.put("Nombre entier", FieldType.INTEGER);
        types.put("Nombre réel", FieldType.NUMERIC);
        types.put("Vrai / Faux", FieldType.BOOLEAN);
        types.put("Date", FieldType.DATETIME);
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

        setBounds(0,0,800, 431);

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


        JLabel title = new JLabel("<html>Bienvenue dans l'assistant de création de table</html>");
        title.setFont(new Font("Serif", Font.BOLD, 22));
        title.setPreferredSize(new Dimension(600, 50));
        panel.add(title, "gapbottom 10, wrap");

        JLabel intro = new JLabel("<html>Cet assistant va vous guider dans la procédure de création" +
                " de votre table. Suivez les indications, parcourrez les étapes et vous " +
                "aurez créé votre table en toute simplicité.</html>");
        intro.setPreferredSize(new Dimension(600, 300));
        intro.setVerticalAlignment(SwingConstants.TOP);
        panel.add(intro);

        return panel;
    }

    /**
     * This method create the headers of the table
     * that displays fields.
     * @param migLayoutParam The MigLayout constraint for headers
     * @param headers The list of headers (String)
     */
    public void createHeaders(String migLayoutParam, java.util.List<String> headers)
    {
        for(String header : headers)
        {
            JLabel headerLabel = new JLabel(header);
            headerLabel.setOpaque(true);
            headerLabel.setBackground(new Color(177, 205, 241));
            headerLabel.setBorder(BorderFactory.createLineBorder(Color.white, 1));
            headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
            table.add(headerLabel, migLayoutParam);
        }
    }

    /**
     * This method create the first step after the introduction for the wizard.
     * This step allow user to choose the name of his table and
     * to define the differents fields.
     * @return It returns the JPanel that contains the form
     */
    private JPanel createPanelStep1()
    {
        panel = new JPanel();
        panel.setLayout(new MigLayout("fill, inset 0"));

        JPanel p = new JPanel(new MigLayout("fill"));
        p.add(new JLabel("Nom de votre table :"));
        tfTable = new JTextField();
        p.add(tfTable, "growx");
        panel.add(p, "dock north, gapbottom 20, gaptop 30");

        panel.add(new JLabel("Rentrez ci-dessous les champs de votre " +
                "table que vous souhaitez."), "dock north, gapbottom 20");

        table = new JPanel();
        table.setLayout(new MigLayout("fillx, gapx 0!"));

        ArrayList<String> headers = new ArrayList<String>(
                Arrays.asList("Supprimer", "Nom", "Type", "Champ vide possible", "Champ par défaut"));
        createHeaders("growx", headers.subList(0, headers.size() - 1));
        createHeaders("growx, wrap", headers.subList(headers.size() - 1, headers.size()));

        addField();

        jsp = new JScrollPane(table);
        Border border = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        jsp.setBorder(border);
        panel.add(jsp, "dock north, grow");

        JButton ajouterBtn = new JButton("Ajouter un champs");
        ajouterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addField();
                repaintRow();
            }
        });
        panel.add(ajouterBtn, "wrap, growx");

        return panel;
    }

    /**
     * This method add a blank field for the new table in the
     * displayed array of the wizard.
     */
    private void addField()
    {
        rowCount++;
        Color c = rowCount % 2 == 0 ? color2 : color1;

        JPanel panel0 = new JPanel(new MigLayout());
        panel0.setName("supprimer_" + rowCount);
        panel0.setBackground(c);
        JButton suppr = new JButton(new ImageIcon("src/view/wizard/img/supprimerIcon.png"));
        suppr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createComponentMap();
                Component c = ((Component)e.getSource()).getParent();
                String[] splitted = c.getName().split("_");
                int index = Integer.parseInt(splitted[splitted.length - 1]);
                table.remove(getComponentByName("supprimer_" + index));
                table.remove(getComponentByName("nom_" + index));
                table.remove(getComponentByName("type_" + index));
                table.remove(getComponentByName("nullable_" + index));
                table.remove(getComponentByName("default_" + index));
                repaintRow();
            }
        });
        panel0.add(suppr, "growx");
        table.add(panel0, "growx");

        JPanel panel = new JPanel(new MigLayout("fill"));
        panel.setName("nom_" + rowCount);
        panel.setBackground(c);
        TextField name = new TextField();
        panel.add(name, "growx");
        table.add(panel, "growx");

        JPanel panel1 = new JPanel(new MigLayout("fill"));
        panel1.setName("type_" + rowCount);
        panel1.setBackground(c);
        JComboBox cbTypes = new JComboBox();
        for(String type : types.keySet())
            cbTypes.addItem(type);
        panel1.add(cbTypes, "growx");
        table.add(panel1, "growx");

        JPanel panel2 = new JPanel(new MigLayout("fill"));
        panel2.setName("nullable_" + rowCount);
        panel2.setBackground(c);
        JCheckBox chx = new JCheckBox();
        panel2.add(chx, "align center");
        table.add(panel2, "align center, growx");

        JPanel panel3 = new JPanel(new MigLayout("fill"));
        panel3.setName("default_" + rowCount);
        panel3.setBackground(c);
        TextField def = new TextField();
        panel3.add(def, "growx");
        table.add(panel3, "growx, wrap 0");
    }

    /**
     * This method refresh the component which displays
     * the array of fields.
     * It's used when we had a field in the array.
     */
    private void repaintRow() {
        panel.updateUI();
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


        JLabel title = new JLabel("<html>Préparation à la création de table réussi</html>");
        title.setFont(new Font("Serif", Font.BOLD, 22));
        title.setPreferredSize(new Dimension(600, 50));
        panel.add(title, "gapbottom 10, wrap");

        JLabel end = new JLabel("<html>Vous avez terminé l'assistant de création de table " +
                "avec succès. Cliquez sur \"Terminer\" pour finaliser la création de" +
                " votre nouvelle table.</html>");
        end.setPreferredSize(new Dimension(600, 300));
        end.setVerticalAlignment(SwingConstants.TOP);
        panel.add(end);

        return  panel;
    }

    /**
     * This method create the logo that figure on the left of the
     * wizard. It represent a table.
     * @return It returns the JComponent that displays the logo
     */
    private JComponent createLogo()
    {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(new ImageIcon("src/view/wizard/img/table.jpg"));
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
        if (index == 1)
        {
            nomTable = tfTable.getText();
            // Check du nom de la table
            if (Pattern.matches("^[a-zA-Z0-9_]+$", tfTable.getText()))
            {
                Table tab = model.createTable(nomTable);
                // Check des champs
                for(int i = 5; i < table.getComponentCount(); i += 5)
                {
                    String name = new String();
                    FieldType type = FieldType.VARCHAR;
                    Boolean nullable = false;
                    String defaultValue = "";

                    JPanel j = (JPanel)table.getComponent(i + 1);
                    if (j.getName().contains("nom"))
                        name = ((TextField)j.getComponent(0)).getText();
                    j = (JPanel)table.getComponent(i + 2);
                    if (j.getName().contains("type"))
                        type = types.get((String) ((JComboBox) j.getComponent(0)).getSelectedItem());
                    j = (JPanel)table.getComponent(i + 3);
                    if (j.getName().contains("nullable"))
                        nullable = ((JCheckBox)j.getComponent(0)).isSelected();
                    j = (JPanel)table.getComponent(i + 4);
                    if (j.getName().contains("default"))
                        defaultValue = ((TextField)j.getComponent(0)).getText();

                    Object defVal = convertDefaultValueType(type, defaultValue);
                    if (defVal instanceof Exception)
                        break;
                    tab.createField(name, type, nullable, defVal);
                }
                return true;
            }
            return false;
        }
        return true;
    }

    /**
     * This method is called when the user click on the
     * "Finish" button. In this method, we finalize the modification
     * of the DatabaseModel object and we set the name of the table as
     * the return value of this wizard.
     * @see DatabaseModel
     */
    @Override
    protected void wizardEnding()
    {
        result = nomTable;
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
        model.removeTable(nomTable);
    }

    /**
     * This method create an HashMap of all
     * components of the display array of fields
     * and associate the name of the component as the key.
     */
    private void createComponentMap() {
        componentMap = new HashMap<String,Component>();
        Component[] components = table.getComponents();
        for (int i=0; i < components.length; i++)
            componentMap.put(components[i].getName(), components[i]);
    }

    /**
     * This method return the component of the HashMap
     * of the createComponentMap method with the name of
     * the component.
     * @param name The name of the component
     * @return The component of the HashMap
     */
    public Component getComponentByName(String name) {
        return componentMap.containsKey(name) ?
                (Component) componentMap.get(name) :
                null;
    }

    /**
     * This method convert a string into the type of
     * we want.
     * @param type The desired type of the value
     * @param value The value to convert
     * @return The object converted
     */
    private Object convertDefaultValueType(FieldType type, String value)
    {
        try
        {
            switch (type)
            {
                case VARCHAR:
                    return value.length() == 0 ? null : value;
                case BOOLEAN:
                    return Boolean.getBoolean(value);
                case INTEGER:
                    return Integer.getInteger(value);
                case DATETIME:
                    return new SimpleDateFormat(value);
                case NUMERIC:
                    return Float.parseFloat(value);
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, "La valeur " + value + " n'est pas valide ne tant que " +
                    "valeur par défaut.");
            return e;
        }
        return null;
    }
}