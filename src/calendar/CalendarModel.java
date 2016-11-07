package calendar;

import javax.swing.table.AbstractTableModel;
import java.util.Calendar;

/**
 * Handles logic of days, months, and years.
 */
public class CalendarModel extends AbstractTableModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7570145137639096909L;
	private String[] days = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
    int[] daysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    private String[][] layout = new String[7][7];

    public CalendarModel(int m, int y) {
        int firstDay = getFirstDayOfMonth(m, y);
        int dayCount = 1;
        int dayMax = daysInMonth[m];
        if (m == 1 && (y % 4) == 0) {
            dayMax++;
        }
        for (int j = firstDay; j < 7; j++) {
            layout[1][j] = String.valueOf(dayCount);
            dayCount++;
        }
        for (int i = 2; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (dayCount < dayMax + 1) {
                    layout[i][j] = String.valueOf(dayCount);
                    dayCount++;
                }
            }
        }
    }

    public int getFirstDayOfMonth(int m,int y){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, m);
        c.set(Calendar.YEAR, y);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        String d = c.getTime().toString();
        System.out.println(d);
        if(d.contains("Mon")){
            return 1;
        }
        if(d.contains("Tue")){
            return 2;
        }
        if(d.contains("Wed")){
            return 3;
        }
        if(d.contains("Thu")){
            return 4;
        }
        if(d.contains("Fri")){
            return 5;
        }
        if(d.contains("Sat")){
            return 6;
        }
        else{
            return 0;
        }
    }

    public int getRowCount() {
        return 7;
    }

    public int getColumnCount() {
        return 7;
    }

    public Object getValueAt(int row, int column) {
        if(row == 0){
            return days[column];
        }
        if(layout[row][column] != "0"){
            return layout[row][column];
        }
        else return null;
    }

}
