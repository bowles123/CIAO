package database;

import api.CanvasAPI;
import org.json.JSONArray;
import java.util.Vector;

/**
 * Hold all information for user.
 */

public class User{
    private Vector<Course> courseList = new Vector<Course>();
    private String token;
    private String id;
    private String name;
    private String avatarURL;
    
    public User() {
    	token = "";
    	courseList.addElement( new Course("Course 1") );
    	courseList.addElement( new Course("Course 2") );
    }

    public User(String t){
        token = t;
        t = "";

        /** Modifying JSON fields to work with parser, errors result if does not start with capital character. */
        char[] c = CanvasAPI.getFavoriteCourses(token).toCharArray();
        for (int i = 0; i < c.length; i++) {
            char a = '\"';
            if (a == c[i]) {
                c[i + 1] = Character.toUpperCase(c[i + 1]);
            }
        }
        String d = new String(c);

        JSONArray courses = new JSONArray(d);
        for(int i = 0;i < courses.length(); i++){
                Course test = new Course(courses.getJSONObject(i),token);
                courseList.addElement(test);
        }
        token = "";

        /** Check that all course threads have finished running. */
        boolean running = true;
        while(running){
            running = false;
             for(int i = 0;i < courseList.size();i++){
                if(courseList.get(i).runningStatus() == true){
                    running = true;
                }
             }
        }
    }

    public Vector<Course> getCourseList(){
        return courseList;
    }

    public String getId(){ return id;}
    public void setId(String i){id = i;}

    public String getName(){return name;}
    public void setName(String n){name = n;}

    public String getAvatarURL(){return avatarURL;}
    public void setAvatarURL(String a){avatarURL = a;}
}

