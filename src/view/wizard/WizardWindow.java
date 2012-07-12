package view.wizard;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * WizardWindow is the window wich allow
 * other classes to implement it and dispose
 * to a window of wizard.
 * This window is modal, it extends JDialog
 * @see JDialog
 * @author fconstant
 */
public class WizardWindow extends JDialog {

    private JPanel panelLeft;
    private JPanel panelRight;
    private JPanel panelBottom;
    private JButton btnPrcdent;
    private JButton btnSuivant;
    private JButton btnAnnuler;
    private int indCurrentStep = 0;

    protected Object result;

    /**
     * This method intitialize the wizard
     * and show the window.
     * @return Return an object as a result of the wizard
     */
    public Object ShowDialog()
    {
        pack();
        setVisible(true);
        return result;
    }

    /**
     * This is the constructor of the WizardWindow class.
     * It initialize the window of this wizard.
     * @param component This is the component that will be the owner of the WizardWindow (JDialog)
     */
    public WizardWindow(Component component) {
        super(JOptionPane.getFrameForComponent(component), true);

        initialize();

        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new MyWindowListener());
    }

    /**
     * This method allow the user of the class to
     * add a JComponent to left panel of the wizard.
     * @see JComponent
     * @param component The component to add
     */
    public void AddLeftContent(Component component)
    {
        panelLeft.add(component);
    }

    /**
     * This method allow the user of the class to
     * add a JComponent to right panel of the wizard.
     * So this method add a step to the wizard (it add a
     * JComponent to the CardLayout).
     * @see JComponent
     * @param component The component to add
     */
    public void AddStep(Component component)
    {
        panelRight.add(component, component.toString());
    }

    /**
     * This method initialize the window
     */
    private void initialize() {
        setBounds(100, 100, 600, 431);
        initPanels();
        initButtons();
    }

    /**
     * This method initialize the panels (left and right)
     */
    private void initPanels()
    {
        getContentPane().setLayout(new MigLayout("insets 0, fill"));

        panelLeft = new JPanel();
        //getContentPane().add(panelLeft, "gapleft 10, w 167px!");
        getContentPane().add(panelLeft, "gapleft 10");

        panelRight = new JPanel(new CardLayout());
        //getContentPane().add(panelRight,"gapleft 10, w 400px!, wrap, grow");
        getContentPane().add(panelRight,"gapleft 10, wrap, grow");

        panelBottom = new JPanel();
        getContentPane().add(panelBottom,"span, align right");
        panelBottom.setLayout(new MigLayout());
    }

    /**
     * This method initialize the buttons (Previous, Next, Cancel)
     */
    private void initButtons()
    {
        btnPrcdent = new JButton("Pr\u00E9c\u00E9dent");
        btnPrcdent.setEnabled(false);
        btnPrcdent.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                ((CardLayout)panelRight.getLayout()).previous(panelRight);
                indCurrentStep--;
                if (indCurrentStep == 0)
                {
                    btnPrcdent.setEnabled(false);
                }
                else if (indCurrentStep != (panelRight.getComponentCount() - 1))
                {
                    btnSuivant.setText("Suivant");
                }
            }
        });

        panelBottom.add(btnPrcdent);

        btnSuivant = new JButton("Suivant");
        btnSuivant.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!btnPrcdent.isEnabled())
                    btnPrcdent.setEnabled(true);
                // TODO : Check validité
                // Vérifier si on peut passer au suivant?
                if (indCurrentStep != (panelRight.getComponentCount() - 1) && validateStep(indCurrentStep))
                {
                    ((CardLayout)panelRight.getLayout()).next(panelRight);
                    indCurrentStep++;
                    if (indCurrentStep == (panelRight.getComponentCount() - 1))
                        btnSuivant.setText("Terminer");
                }
                else if (indCurrentStep != (panelRight.getComponentCount() - 1))
                    JOptionPane.showMessageDialog(WizardWindow.this, "Cette étape n'est pas correctement remplis" +
                            ". Vérifiez votre saisie.");
                else
                {
                    wizardEnding();
                    setVisible(false);
                    dispose();
                }
            }
        });
        panelBottom.add(btnSuivant);

        btnAnnuler = new JButton("Annuler");
        btnAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wizardClosing();
                setVisible(false);
                dispose();
            }
        });
        panelBottom.add(btnAnnuler);
    }

    /**
     * This method allow the user of this class to
     * check the validity of differents step when user
     * click to the next button.
     * @param index The index of the current step
     * @return A boolean that indicate if the step is well filled or not
     */
    protected  boolean validateStep(int index)
    {
        return false;
    }

    /**
     * This method allow the user of this class to
     * do some code when the user click the Finish button.
     */
    protected void wizardEnding()
    {
    }

    /**
     * This method allow the user of this class to
     * do some code when the user left the wizard
     * without finish the wizard.
     */
    protected void wizardClosing()
    {
    }

    /**
     * This class is used to intercept the windowClosing event.
     * @author fconstant
     */
    private static class MyWindowListener extends WindowAdapter {
        /**
         * This method redefine the windowClosing event.
         * We ask the user if he really want to left the wizard
         * without finish it.
         * @see WindowEvent
         * @param e WindowEvent of this event
         */
        public void windowClosing(WindowEvent e) {
            WizardWindow dialog = (WizardWindow)e.getSource();
            if ( JOptionPane.showConfirmDialog(dialog, "Etes-vous sûr de vouloir quitter l'assistant ? " +
                    "Toutes les opérations seront annulées.", "Quitter l'assistant ?",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)  == JOptionPane.YES_OPTION)
            {
                dialog.wizardClosing();
                dialog.setVisible(false);
                dialog.dispose();
            }
        }
    }
}
