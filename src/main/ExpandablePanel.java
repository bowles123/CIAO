package main;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Locale;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import database.Announcement;
import database.Assignment;
import database.Quiz;


public class ExpandablePanel extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static final ImageIcon show = new ImageIcon("/lib/expand.png", "Expand");
	private static final ImageIcon hide = new ImageIcon("/lib/collapse.png", "Collapse");
	private static final Border raised = BorderFactory.createBevelBorder(BevelBorder.RAISED);
	private static final Border lowered = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
	public static final int DEFAULT_WIDTH = 1;

	private boolean selected;
	private HeaderPanel header;
	private ContentPanel content;

	// private Assignment assign;
	// private Quiz quiz;
	public ExpandablePanel() {
		this(HeaderPanel.DEFAULT, ContentPanel.DEFAULT, null);
	}
	public ExpandablePanel(Assignment a) {
		this( a.getName(), a.getDescription(), a.getDueDate());
	}
	public ExpandablePanel(Quiz q) {
		this( q.getTitle(), q.getDescription(), q.getDueDate());
	}
	public ExpandablePanel(Announcement an) {
		this( an.getTitle(), an.getMessage(), an.getDate());
	}
	public ExpandablePanel(String title, String desc, Calendar date) {
		selected = false;

		header = new HeaderPanel( title, date );
		content = new ContentPanel(desc, DEFAULT_WIDTH);

//		setBorder( BorderFactory.createLineBorder(Color.BLACK) );
		setBorder(ExpandablePanel.raised);

		content.setVisible(false);
		setLayout(new BorderLayout(2, 2));

		add(this.header, BorderLayout.NORTH);
		add(this.content, BorderLayout.CENTER);
	}

	public void toggle() {
		selected = !selected;
		if( content.isShowing() ) {
			header.setIcon(show);
			content.setVisible(false);
			setBorder(ExpandablePanel.raised);
		}
		else {
			header.setIcon(hide);
			content.setVisible(true);
			setBorder(ExpandablePanel.lowered);
		}
		validate();
		header.repaint();
	}

	public void update(Assignment a) {
		header.setText( a.getName(), a.getDueDate() );
		content.setText( a.getDescription() );
	}
	public void update(Quiz q) {
		header.setText( q.getTitle(), q.getDueDate() );
	}
	private class HeaderPanel extends JLabel implements MouseListener {
		private static final long serialVersionUID = 1L;
		private static final String DEFAULT = "Title";

//		public HeaderPanel(String s) {
//			this(s, null);
//		}
		public HeaderPanel(String s, Calendar d) {
			super(s);
			addMouseListener(this);

			setText(s, d);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			toggle();
		}

		public void setText(String s, Calendar d) {
			if(d != null)
				setText(s + " (Due: " + getDate(d) + ")");
			else
				setText(s);
		}

		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}

		private String getDate(Calendar d) {
			StringBuilder result = new StringBuilder();
			Locale l = getLocale();
			result.append( d.getDisplayName(Calendar.MONTH, Calendar.SHORT, l) );
			result.append( " " );
			result.append( d.get(Calendar.DAY_OF_MONTH) );
			return result.toString();
		}
	}
	private class ContentPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public static final String DEFAULT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum dui elit, porta sed turpis non, laoreet maximus massa. Vivamus nulla massa, facilisis semper volutpat sed, pharetra quis diam. Curabitur lorem nunc, dapibus at pulvinar eu, porta sed turpis. Nam a neque pellentesque, molestie leo sit amet, consequat neque. Phasellus in convallis nisl, at condimentum lacus. Mauris sapien est, pellentesque ac tempor et, auctor sed nisl. In hac habitasse platea dictumst. Phasellus ex odio, malesuada ac ipsum quis, aliquam ornare dui. Sed nunc velit, facilisis a blandit eu, sollicitudin ut neque. Praesent tortor mi, commodo non velit nec, maximus fermentum massa. Vestibulum molestie quam non egestas pulvinar. Aliquam faucibus viverra tempor.\n\nVestibulum quam nulla, fringilla rhoncus neque ut, mollis semper nulla. Nulla mattis, eros consequat gravida ullamcorper, sapien orci viverra sapien, a semper metus ligula at tortor. Sed non venenatis diam. Nulla ultrices lorem non nisl porta aliquam. Cras ultrices fringilla dolor egestas ornare. Mauris augue erat, commodo id felis vel, accumsan ultricies purus. Suspendisse potenti. Suspendisse potenti. Quisque semper velit sit amet gravida egestas.\n\nAliquam volutpat justo suscipit dignissim fermentum. Proin tempor lacus nec laoreet rhoncus. Aliquam non justo leo. Duis efficitur mattis eros, quis vestibulum lacus bibendum sit amet. Pellentesque eu tempus eros, quis ultrices dolor. Nullam arcu tellus, fermentum sit amet gravida vitae, blandit sit amet leo. Praesent nec lorem non erat venenatis porta. Sed ultricies volutpat metus eu imperdiet. Phasellus posuere placerat dignissim. Curabitur nec purus ante. Mauris commodo libero vel luctus dapibus. Morbi fringilla eu nunc molestie eleifend. Ut pellentesque facilisis diam eleifend semper. Fusce urna arcu, imperdiet sit amet purus eu, accumsan tempor lorem. Nunc eu ligula leo. Cras eleifend nibh quam, nec consequat mauris dignissim eget.\n\nCras tincidunt aliquet libero, eu semper tellus tempor et. Fusce mauris magna, semper ac gravida et, faucibus posuere nunc. Integer vel ante vitae lorem consectetur elementum vestibulum sit amet purus. Aliquam vel nunc blandit, lobortis elit non, tempus nunc. Mauris lobortis nulla facilisis massa hendrerit posuere. Aenean pellentesque placerat arcu, eu auctor nisi. Phasellus sem enim, eleifend sed metus id, varius aliquam ante. Proin ultrices tincidunt venenatis. Proin lobortis sed est in sodales.\n\nProin tincidunt orci eget ex sagittis euismod. Integer at lacus urna. Duis porta non dolor a pretium. Integer facilisis eleifend ex, ac malesuada ipsum. Nulla quam libero, vulputate at pretium vel, mattis ut massa. Nam imperdiet malesuada ligula in congue. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Proin neque nunc, dictum posuere nunc tincidunt, vehicula tempus neque.";
		private static final int LINES = 20;

		private JTextArea text;
		private JScrollPane scrollbar;

		public ContentPanel(String desc, int width) {
 			super();
 			SpringLayout layout = new SpringLayout();
 			setLayout(layout);
 			text = new JTextArea(ContentPanel.LINES, width);
			text.setText(desc);
 			text.setEditable(false);
 			text.setLineWrap(true);
 			text.setWrapStyleWord(true);
 			scrollbar = new JScrollPane(text);
 			scrollbar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
 			add(scrollbar);
 			layout.putConstraint(SpringLayout.NORTH, this, 5, SpringLayout.NORTH, scrollbar);
 			layout.putConstraint(SpringLayout.SOUTH, this, 5, SpringLayout.SOUTH, scrollbar);
 			layout.putConstraint(SpringLayout.WEST, this, 5, SpringLayout.WEST, scrollbar);
 			layout.putConstraint(SpringLayout.EAST, this, 5, SpringLayout.EAST, scrollbar);
		}

		public void setText(String s) {
			text.setText(s);
		}
	}
}

class TestEP {
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(360, 500);
		f.setLocation(200, 100);
		ExpandablePanel panel = new ExpandablePanel();
		f.getContentPane().add(new JScrollPane(panel));
		f.setVisible(true);
	}
}