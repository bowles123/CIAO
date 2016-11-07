package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.BorderFactory;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;

public class TabButton extends JButton implements ActionListener 
{
	private final TabPanels panel;
	private final JTabbedPane pane;
	private final JComboBox<String> elementDropBox;
	private final comboBoxElement tempElement;
	private static final long serialVersionUID = 1L;

	protected TabButton(TabPanels panel, JTabbedPane pane, JComboBox<String> elementDropBox, comboBoxElement tempElement) {
		
        int size = 21;
        
        this.panel = panel;
        this.pane = pane;
        this.elementDropBox = elementDropBox;
        this.tempElement = tempElement;
        
        setPreferredSize(new Dimension(size, size));
        setToolTipText("close this tab");
        setUI(new BasicButtonUI());
        setContentAreaFilled(false);
        setFocusable(false);
        setBorder(BorderFactory.createEtchedBorder());
        setBorderPainted(false);
        setRolloverEnabled(true);
        
        addMouseListener(buttonMouseListener);
        addActionListener(this);
    }

    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        //shift the image for pressed buttons
        if (getModel().isPressed()) {
            g2.translate(1, 1);
        }
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLACK);
        if (getModel().isRollover()) {
            g2.setColor(Color.MAGENTA);
        }
        int delta = 6;
        g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
        g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
        g2.dispose();
    }
    
    public void actionPerformed(ActionEvent e) {
    	// get the selected panel (Main GUI JTabbedPane 'pane' -> Selected Tab / TabPanels component 'panel')
        int i = pane.indexOfTabComponent(panel);
        if (i > -1) {
        	// Spit selected TabPanels component's title back into Add Tab / Course drop down
        	elementDropBox.addItem(tempElement.getName());
        	// Removes tab
            pane.remove(i);
        }
    }

	private final static MouseListener buttonMouseListener = new MouseAdapter() {
	    public void mouseEntered(MouseEvent e) {
	        Component component = e.getComponent();
	        if (component instanceof AbstractButton) {
	            AbstractButton button = (AbstractButton) component;
	            button.setBorderPainted(true);
	        }
	    }
	
		public void mouseExited(MouseEvent e) {
	        Component component = e.getComponent();
	        if (component instanceof AbstractButton) {
	            AbstractButton button = (AbstractButton) component;
	            button.setBorderPainted(false);
	        }
		}
	};
}
