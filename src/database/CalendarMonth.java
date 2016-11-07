package database;

import java.util.Calendar;
import java.util.Vector;

/**
 * Holds assignment dates for month and course indicated in constructor.
 */

public class CalendarMonth {
    private int month;
    private Course course;
    private Vector<Assignment> dates = new Vector<Assignment>(1,1);

    CalendarMonth(int m, Course c){
        month = m;
        course = c;
        for(int i = 0; i < course.getAssignmentList().size(); i ++){
            int md = -1;
            try {
                Calendar dueDate = course.getAssignmentList().get(i).getDueAt();
                md = dueDate.get(Calendar.MONTH);
            } catch(NullPointerException e){}
            if(md == month){
               dates.addElement(course.getAssignmentList().get(i));
            }

        }
    }

    public Vector<Assignment> getDates (){return dates;}

}
