package calendar;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;

/**
 * Sets table properties. Uses CalendarModel.
 */

public class CalendarTable extends JTable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CalendarTable(int m, int y) {
        super();
        CalendarModel model = new CalendarModel(m, y);
        this.setModel(model);
        this.setTableHeader(null);
        this.setPreferredScrollableViewportSize(new Dimension(665, 665));
        this.setRowHeight(95);
        for(int i = 0; i < 7 ; i ++ ) {
            TableColumn column = this.getColumnModel().getColumn(i);
            column.setPreferredWidth(95);
            column.setMaxWidth(95);
            column.setMinWidth(95);
        }
    }
}


