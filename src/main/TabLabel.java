package main;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

// Separated for ease of digestion in TabPanels class
public class TabLabel extends JLabel
{
	private static final long serialVersionUID = 1L;
	
	protected TabLabel(String tempTitle)
	{
		setHorizontalAlignment(SwingConstants.LEFT);
		this.setDoubleBuffered(true);
		this.setText(tempTitle);
	}
}