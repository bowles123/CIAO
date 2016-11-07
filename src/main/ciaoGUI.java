/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;
import java.awt.Component;

import database.User;
import database.Announcement;
import database.Assignment;
import database.Quiz;

import calendar.CalendarPanel;

import net.miginfocom.swing.MigLayout;
import java.util.Vector;
import org.eclipse.wb.swing.FocusTraversalOnArray;

public class ciaoGUI extends javax.swing.JFrame
{
	// No idea why Eclipse wanted this here
	private static final long serialVersionUID = 1L;
	private static final int MIN_WIDTH = 400;
	private static final int MIN_HEIGHT = 600;

	public ciaoGUI(String token)
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(ciaoGUI.class.getResource("/main/icon.png")));
		this.setTitle("CIAO - Canvas Interactive Assignment Organizer");
		setMinimumSize( new Dimension(MIN_HEIGHT, MIN_WIDTH) );
	    initComponents(token);
    }


    private void initComponents(String token)
    {
        calendarButton = new JButton();
        calendarButton.setFocusable(true);

        addTabButton = new javax.swing.JButton();
        addTabButton.setFocusable(true);

        addCourseButton = new javax.swing.JButton();
        addCourseButton.setFocusable(true);

        tabComboBox = new JComboBox<String>();
        tabComboBox.setMaximumRowCount(20);
        tabComboBox.setFocusable(true);
        tabComboBox.addItem(null);

        courseComboBox = new JComboBox<String>();
        courseComboBox.setMaximumRowCount(20);
        courseComboBox.setFocusable(true);
        courseComboBox.addItem(null);

        tabbedPane = new javax.swing.JTabbedPane();
        tabbedPane.setDoubleBuffered(true);
        tabbedPane.setTabPlacement(JTabbedPane.TOP);
        tabbedPane.setPreferredSize(new Dimension(100,100));
        tabbedPane.setFocusable(true);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // Event Handler Instantiations
        addTabButton.setText("Add Tab");
        addTabButton.addActionListener(new ActionListener() {
        	public void actionPerformed (ActionEvent e) {
        		addTabButtonClicked(e);
        	}
        });
        addCourseButton.setText("Add Course");
        addCourseButton.addActionListener(new ActionListener() {
        	public void actionPerformed (ActionEvent e) {
        		addCourseButtonClicked(e);
        	}
        });

		calendarButton.setText("Open Calendar");
		calendarButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				calendarButtonClicked(evt);
			}
		});

        tabbedPane.setBackground(Color.DARK_GRAY);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(Alignment.LEADING, layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        				.addComponent(courseComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(tabComboBox, 0, 192, Short.MAX_VALUE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(addTabButton)
        					.addPreferredGap(ComponentPlacement.RELATED, 399, Short.MAX_VALUE)
        					.addComponent(calendarButton))
        				.addComponent(addCourseButton))
        			.addContainerGap())
        		.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(6)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(tabComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(addTabButton)
        				.addComponent(calendarButton))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(courseComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(addCourseButton))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE))
        );

		setAccessToken(token);
        initTabs();

        getContentPane().setLayout(layout);
        getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{tabComboBox, addTabButton, courseComboBox, 
        																				   addCourseButton, calendarButton, tabbedPane}));

        pack();
    }

    private static void setAccessToken(String token)
    {
		if (token.startsWith("1009~"))
			ciaoGUI.accessToken = token;
		else
			JOptionPane.showMessageDialog(null, "Invalid token.");
    }

    private static void addTabButtonClicked(ActionEvent evt) {
    	// Make sure the selected comboBox entry is not empty
    	if (tabComboBox.getSelectedItem() != null) {
    		// Make sure there are enough tabs to avoid crashes (an empty screen counts as a tab - hence the 1)
	    	if (tabbedPane.getComponentCount() > 1) {
	    		JTabbedPane tempTab = (JTabbedPane) tabbedPane.getSelectedComponent();
	    		// Prevents adding of tab based on empty comboBox
	    		if (tabComboBox.getItemCount() > 0) {
	    			Vector<comboBoxElement> vectorAtIndex = tabElements.get(tabbedPane.getSelectedIndex());

	    			// Get the index of the appropriate tab; right now, between 'Assignments' and 'Quizzes'
	    			int i = 0;
	    			while (vectorAtIndex.get(i).getName() != tabComboBox.getSelectedItem()) {
	    				i++;
	    			}
	    			comboBoxElement tempElement = vectorAtIndex.get(i);

	    			// Check that the tab element being added belongs in the course tab it's being added to
	    			// (This was hard...)
	    			if (tabbedPane.getSelectedIndex() == tempElement.getPlace()) {
		        		addOneTab((JTabbedPane) tabbedPane.getSelectedComponent(),
		        				  tempElement, tempTab.getComponentCount() - 1);

		        		tabComboBox.removeItem(tabComboBox.getItemAt(tabComboBox.getSelectedIndex()));
	    			}
	    		}
	    	}
    	}
    }

	protected void addCourseButtonClicked(ActionEvent e) {
		if (courseComboBox.getSelectedItem() != null) {
			if (courseComboBox.getItemCount() > 1) {
				// Get appropriate index of course; cycles through all 10 courses till it finds it
				int courseIndex = 0;
				while (courseElements.get(courseIndex).getName() != courseComboBox.getSelectedItem()) {
					courseIndex++;
				}

				addOneCourse(courseElements.get(courseIndex));
	    		courseComboBox.removeItem(courseComboBox.getSelectedItem());
			}
		}
	}

	protected void calendarButtonClicked(MouseEvent evt) {
		CalendarPanel.createAndShowGUI(tempUser);
	}

	private static void initTabs() {
		tempUser = new User(accessToken);
		accessToken = "";

		tabElements = new Vector<Vector<comboBoxElement>>();
		courseElements = new Vector<comboBoxElement>();
		int courseCount = tempUser.getCourseList().size();
		int coursePlace = 0;
		for (int courseIndex = 0; courseIndex < courseCount; courseIndex++) {
			System.out.println("Pass " + coursePlace);
			// Created, filled in nested loop, added to global tabbedPane, then destroyed by scope
			JTabbedPane tempTab = new JTabbedPane();

			// Temporarily adds course information for in-loop access
			allLists.add(tempUser.getCourseList().get(courseIndex).getAssignmentList());
			allLists.add(tempUser.getCourseList().get(courseIndex).getQuizList());
			allLists.add(tempUser.getCourseList().get(courseIndex).getAnnouncementList());

			String courseName = tempUser.getCourseList().get(courseIndex).getName();
			String[] tabNames = {"Assignments", "Quizzes", "Announcements"};

			Vector<comboBoxElement> tempTabVector = new Vector<comboBoxElement>();

			for (int i = 0; i < tabNames.length; i++) {
				// Creates comboBoxElement and adds it to temporary Vector
				tempTabVector.add(new comboBoxElement(tabNames[i], courseIndex));

				// Creates a full, filled panel, adds that panel to the tempTab
				// collection, then formats it with close button and title label
				// using the TabPanels class
				tempTab.add(createPanel(tempTabVector.get(i), allLists.get(i)));
				tempTab.setTabComponentAt(i, new TabPanels(tempTab, tabComboBox,
														   tempTabVector.get(i)));
				tempTab.getTabComponentAt(i).setFocusable(true);
			}

			// Adds scoped temporary vector to global tabElements vector
			// Adds scoped temporary tab collection to global tabbedPane collection
			tabElements.add(tempTabVector);
			tabbedPane.add(tempTab);

			courseElements.add(new comboBoxElement(courseName, coursePlace));
			tabbedPane.setTabComponentAt(coursePlace, new TabPanels(tabbedPane, courseComboBox,
														  		    courseElements.get(coursePlace)));

			// tabbedPane.getTabComponentAt(coursePlace).setFocusable(true);
			coursePlace++;
			allLists.clear();
		}
	}

    private static void addOneTab(JTabbedPane tempTab, comboBoxElement tempElement, int tabIndex) {
    	// Takes the JTabbedPane object tempTab passed to it by the invoking method
    	// and adds a new panel to it, effectively creating a new nested tab.

    	if (tabbedPane.getTabCount() > 0)
    	{
			allLists.add(tempUser.getCourseList().get(tabbedPane.getSelectedIndex()).getAssignmentList());
			allLists.add(tempUser.getCourseList().get(tabbedPane.getSelectedIndex()).getQuizList());
			allLists.add(tempUser.getCourseList().get(tabbedPane.getSelectedIndex()).getAnnouncementList());

			if (tempElement.getName() == "Assignments") {
		    	tempTab.add(createPanel(tempElement, allLists.get(0)));
			}

			else if (tempElement.getName() == "Quizzes") {
		    	tempTab.add(createPanel(tempElement, allLists.get(1)));
			}

			else if (tempElement.getName() == "Announcements") {
		    	tempTab.add(createPanel(tempElement, allLists.get(2)));
			}

    		tempTab.setTabComponentAt(tabIndex, new TabPanels(tempTab, tabComboBox, tempElement));
    		allLists.clear();
    	}
    }

    private static void addOneCourse(comboBoxElement boxElement) {
        // Generates a new Course tab by initializing a JTabbedPane object
        // with a string of titles. The string can be substituted with
        // elements of a list of appropriate sections based on JSON data

    	JTabbedPane tempTab;
		tempTab = new JTabbedPane(JTabbedPane.TOP);

		allLists.add(tempUser.getCourseList().get(boxElement.getPlace()).getAssignmentList());
		allLists.add(tempUser.getCourseList().get(boxElement.getPlace()).getQuizList());
		allLists.add(tempUser.getCourseList().get(boxElement.getPlace()).getAnnouncementList());
		Vector<comboBoxElement> tempTabVector = new Vector<comboBoxElement>();

		String[] tabNames = {"Assignments", "Quizzes", "Announcements"};

		for (int i = 0; i < tabNames.length; i++) {
			comboBoxElement temp1 = new comboBoxElement(tabNames[i], boxElement.getPlace());
			tempTabVector.addElement(temp1);
		}

		for (int k = 0; k < tempTabVector.size(); k++) {
			tempTab.add(createPanel(tempTabVector.get(k), allLists.get(k)));
			tempTab.setTabComponentAt(k, new TabPanels(tempTab, tabComboBox, tempTabVector.get(k)));
		}

        tabbedPane.add(tempTab);
		tabbedPane.setTabComponentAt(tabbedPane.getComponentCount() - 2, new TabPanels(tabbedPane, courseComboBox, boxElement));

    	tempTabVector.clear();
		allLists.clear();
    }

	private static JScrollPane createPanel(comboBoxElement type, Vector<?> fedVector) {
	    // Cleaner way of handling panel creation in other methods
    	JScrollPane tempScroll = new JScrollPane();
    	tempScroll.setFocusable(true);
    	tempScroll.add(new JScrollPane());

    	JPanel list = new JPanel();
    	list = fillTab(list, type, fedVector);

        tempScroll.setViewportView(list);
        tempScroll.getVerticalScrollBar().setUnitIncrement(14);

    	return tempScroll;
    }

	@SuppressWarnings("unchecked")
	private static JPanel fillTab(JPanel list, comboBoxElement type, Vector<?> fedVector) {
		list.setLayout(new MigLayout("flowy, fillx", "[grow,fill]", ""));

		if (type.getName() == "Assignments") {
			Vector<Assignment> assignmentVector = new Vector<Assignment>();
			assignmentVector = (Vector<Assignment>) fedVector;

			for (int i = 0; i < assignmentVector.size(); i++) {
		    	list.add(new ExpandablePanel(assignmentVector.get(i)), "grow");
			}
		}

		else if (type.getName() == "Quizzes") {
			Vector<Quiz> quizVector = new Vector<Quiz>();
			quizVector = (Vector<Quiz>) fedVector;

			for (int i = 0; i < quizVector.size(); i++) {
				list.add(new ExpandablePanel(quizVector.get(i)), "grow");
			}
		}

		else if (type.getName() == "Announcements") {
			Vector<Announcement> announcementVector = new Vector<Announcement>();
			announcementVector = (Vector<Announcement>) fedVector;

			for (int i = 0; i < announcementVector.size(); i++) {
				list.add(new ExpandablePanel(announcementVector.get(i)), "grow");
			}
		}
				return list;
	}

	private static javax.swing.JButton calendarButton;
    private static javax.swing.JButton addTabButton;
    private static javax.swing.JButton addCourseButton;
    private static JComboBox<String> tabComboBox;
    private static javax.swing.JComboBox<String> courseComboBox;
    private static javax.swing.JTabbedPane tabbedPane;
	private static String accessToken;
    private static User tempUser;
    private static Vector<Vector<?>> allLists = new Vector<Vector<?>>();
    private static Vector<Vector<comboBoxElement>> tabElements;
    private static Vector<comboBoxElement> courseElements;
}