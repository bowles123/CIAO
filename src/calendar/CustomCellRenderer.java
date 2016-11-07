package calendar;

import database.User;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.*;
import java.awt.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Creates cells for calendar based on both paramters and built in information of table cells.
 */

public class CustomCellRenderer extends JScrollPane implements TableCellRenderer {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextPane textpane;
    private User user;
    private int month;
    private int currentMonth;
    private Color[] colors = {Color.ORANGE,Color.WHITE,Color.CYAN,Color.LIGHT_GRAY,Color.GREEN,Color.PINK};

    public CustomCellRenderer(User u, int m) {
        user = u;
        month = m;
        Calendar c = new GregorianCalendar();
        currentMonth = c.get(Calendar.MONTH);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        textpane = new JTextPane();
        getViewport().add(textpane);
        setForeground(table.getForeground());
        setBackground(table.getBackground());
        textpane.setForeground(table.getForeground());
        textpane.setBackground(table.getBackground());
        textpane.setBorder(new TitledBorder((String)value));
        Style style = textpane.addStyle("name",null);
        StyledDocument doc = textpane.getStyledDocument();
        StyleConstants.setFontSize(style,9);

        /** Responsible for finding relevant dates from a course's calendarOutlook. Could use clean up. */
        for(int i = 0; i < user.getCourseList().size(); i ++){
            StyleConstants.setForeground(style,colors[i]);
            int c = 0;
            try{
            c = user.getCourseList().get(i).getCalendarOutlook().get(month-currentMonth).getDates().size();
            } catch (Exception e){
            }
            for(int j = 0; j < c; j++) {
                int day = user.getCourseList().get(i).getCalendarOutlook().get(month-currentMonth).getDates().get(j).getDueAt().get(Calendar.DAY_OF_MONTH)-1;
                if((String)value != "Sun" && (String)value != "Mon" && (String)value != "Tue" && (String)value != "Wed" && (String)value != "Thu" && (String)value != "Fri" && (String)value != "Sat" && value != null) {
                    if (day == Integer.parseInt((String) value)) {
                        try{
                            doc.insertString(doc.getLength(),user.getCourseList().get(i).getCalendarOutlook().get(month-currentMonth).getDates().get(j).getName(),style);
                        } catch(BadLocationException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return this;
    }

}
