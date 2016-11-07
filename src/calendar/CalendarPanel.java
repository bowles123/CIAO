package calendar;

import database.User;
import main.ciaoGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

/**
 * Sets layout and runs calendar.
 */

public class CalendarPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static User user;
    private JButton prev;
    private JButton next;
    private JLabel title;
    private Calendar c = Calendar.getInstance();
    private int year = c.get(Calendar.YEAR);
    private int month = c.get(Calendar.MONTH);
    private ActionListener nextL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(month != 12)
                { month++; }
            if(month == 12)
                { month = 0; year++;}
            updateFrame(month,year);
        }
    };
    private  ActionListener prevL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(month == 0)
            { month = 12; year--; }
            if(month != 0)
                { month--; }
            updateFrame(month,year);
        }
    };

    public CalendarPanel(User u) {
        user = u;
        addComponents(month,year);
    }

    public void addComponents(int m,int y){
        removeAll();
        this.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();

        final JTable calendar = new CalendarTable(m,y);

        String columnNames = "ABCDEFG";
        for(int i = 0; i < 7 ; i++) {
            calendar.getColumn(columnNames.substring(i,i+1)).setCellRenderer(new CustomCellRenderer(user,month));
        }

        calendar.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(calendar);

        g.gridy = 1;
        g.gridx = 1;
        prev = new JButton("Prev");
        prev.addActionListener(prevL);
        add(prev,g);

        g.gridx = 2;
        title = createLabel(month,year);
        add(title,g);

        g.gridx = 3;
        next = new JButton("Next");
        next.addActionListener(nextL);
        add(next,g);

        g.gridx = 2;
        g.gridy = 2;
        add(scrollPane,g);
    }

    public JLabel createLabel(int m, int y){
        String monthName = "";
        switch(m){
            case 0: monthName = "January"; break;
            case 1: monthName = "February"; break;
            case 2: monthName = "March"; break;
            case 3: monthName = "April"; break;
            case 4: monthName = "May"; break;
            case 5: monthName = "June"; break;
            case 6: monthName = "July"; break;
            case 7: monthName = "August"; break;
            case 8: monthName = "September"; break;
            case 9: monthName = "October"; break;
            case 10: monthName = "November"; break;
            case 11: monthName = "December"; break;
        }
        JLabel label = new JLabel(monthName + " " + y,SwingConstants.CENTER);
        return label;
    }

    public static void createAndShowGUI(User u) {
        JFrame frame = new JFrame("CalendarPanel");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(ciaoGUI.class.getResource("/main/icon.png")));
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        CalendarPanel newContentPane = new CalendarPanel(u);
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public void updateFrame(int m, int y){
        addComponents(m,y);
        this.revalidate();
        this.repaint();
    }
}