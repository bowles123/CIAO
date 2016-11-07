package main;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;

public class TabPanels extends JPanel {
	private static final long serialVersionUID = 1L;
	private final JTabbedPane tempPane;
	private JLabel label;
	
	public TabPanels(JTabbedPane pane, JComboBox<String> elementBox, comboBoxElement tempElement)
	{
        super(new FlowLayout(FlowLayout.TRAILING, 0, 0));
        if (pane == null) {
            throw new NullPointerException("TabbedPane is null");
        }
	
        this.tempPane = pane;
        setOpaque(false);
        
        // Creates label using custom, external, public class TabLabel
        label = new TabLabel(tempElement.getName());
        add(label);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        // Creates label using custom, external, public class TabButton
        JButton button = new TabButton(TabPanels.this, tempPane, elementBox, tempElement);
        add(button);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
	}
}