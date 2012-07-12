package view.diagram.toolbox;

import view.diagram.FactoryPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This class create and manage the diagram toolbox
 * @author dhercun
 */
public class ToolboxPanel extends JPanel{
    private ArrayList<JToggleButton> buttons;

    /**
     * Constructor of a new ToolboxPanel, used to display diagram tools to the user
     * @param pos The position of the new ToolboxPanel
     */
    public ToolboxPanel(Point pos) {
        this.setLocation(pos);
        this.setSize(new Dimension(90, 70));
        this.setBackground(Color.GRAY);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ToolboxElementMouseListener teml = new ToolboxElementMouseListener(this);
        this.addMouseMotionListener(teml);
        this.addMouseListener(teml);

        buttons = new ArrayList<JToggleButton>();
    }

    /**
     * Build and add buttons, to the toolbar
     * @param label The label displayed on the new button
     * @param tool The TYPE of tool for the new button
     * @see FactoryPanel
     * @return The new created button
     */
    public JToggleButton addButton(String label,final FactoryPanel.TOOL tool) {
        JToggleButton button = new JToggleButton(label);
        button.setMargin(new Insets(2,2,2,2));
        button.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
        button.setName(String.valueOf(tool.getValue()));
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JToggleButton sourceButton = (JToggleButton)e.getSource();
                // if it is a button unselect, we clear the tool
                if(!sourceButton.isSelected())
                    FactoryPanel.activeTOOL = FactoryPanel.TOOL.NONE;
                // if it is a tool selection, we memories whitch one
                else
                {
                    int buttonValue = Integer.valueOf(sourceButton.getName());
                    if(buttonValue == FactoryPanel.TOOL.TABLE.getValue())
                        FactoryPanel.activeTOOL = FactoryPanel.TOOL.TABLE;
                    else if(buttonValue == FactoryPanel.TOOL.LINK.getValue())
                        FactoryPanel.activeTOOL = FactoryPanel.TOOL.LINK;
                    else if(buttonValue == FactoryPanel.TOOL.ANNOTATION.getValue())
                        FactoryPanel.activeTOOL = FactoryPanel.TOOL.ANNOTATION;
                }
                pushButton((JToggleButton)e.getSource());
            }
        };
        button.addActionListener(actionListener);

        buttons.add(button);
        this.add(button);

        return button;
    }

    /**
     * Clear the unselected/selected buttons
     * @param selectedButton The new selected button
     */
    private void pushButton(JToggleButton selectedButton) {
        for(JToggleButton b : buttons) {
            if(selectedButton.equals(b))
                selectedButton.setSelected(selectedButton.isSelected());
            else
                b.setSelected(false);
        }
    }
}
