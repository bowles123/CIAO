package database;

import api.CanvasAPI;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Vector;

/**
 * Holds all relevant information on a course and is runnable as a thread.
 */

public class Course implements Runnable {
    private Vector<Assignment> assignmentList = new Vector<Assignment>(15, 15);
    private Vector<Quiz> quizList = new Vector<Quiz>(15, 15);
    private Vector<Announcement> announcementList = new Vector<Announcement>(15,15);
    private Vector<CalendarMonth> calendarOutlook = new Vector<CalendarMonth>(6);

    private static long default_id = 0;
    private long id;
    private String name;
    private String courseCode;
    private boolean restricted = false;
    private String token;
    private Thread thread;

    public Course(String name) {
        id = default_id++;
        this.name = name;
        courseCode = "unavailable";
        restricted = false;
        assignmentList.addElement(new Assignment("Lab 1", "description"));
        assignmentList.addElement(new Assignment("Lab 2", "another description"));
        quizList.addElement(new Quiz("Quiz 1", "First quiz"));
        quizList.addElement(new Quiz("Quiz 2", "Second quiz"));
    }

    public Course(JSONObject course, String token){
        this.token = token;
        token = "";
        if(course.toString().contains("\"Access_restricted_by_date\":true")){
            id = 0;
            name = "Restricted Class";
            courseCode = "unavailable";
            restricted = true;
        }
        else {
            id = course.getLong("Id");
            name = course.getString("Name");
            courseCode = course.getString("Course_code");
            this.start();
        }
    }

    public boolean runningStatus(){
        if(thread.isAlive()) {
            return true;
        }
        else return false;
    }

    public void run() {
        char[] c;

        /** Modifying JSON fields to work with parser, errors result if does not start with capital character. */
        c = CanvasAPI.getCourseAssignments(token, id).toCharArray();
        for (int i = 0; i < c.length; i++) {
            char a = '\"';
            if (a == c[i]) {
                c[i + 1] = Character.toUpperCase(c[i + 1]);
            }
        }
        String d = new String(c);

        if (d.length() > 0){
            JSONArray assignments = new JSONArray(d);

            for(int i = 0; i < assignments.length(); i++){
                if(!assignments.getJSONObject(i).toString().contains("Quiz_id")) {
                    assignmentList.addElement(new Assignment(assignments.getJSONObject(i)));
                }
            }
        }

        /** Modifying JSON fields to work with parser, errors result if does not start with capital character. */
        c = CanvasAPI.getCourseQuizzes(token, id).toCharArray();
        for (int i = 0; i < c.length; i++) {
            char a = '\"';
            if (a == c[i]) {
                c[i + 1] = Character.toUpperCase(c[i + 1]);
            }
        }
        d = new String(c);
        
        if (d.length() > 0){
            JSONArray quizzes = new JSONArray(d);

            for(int i = 0; i < quizzes.length(); i++){
                quizList.addElement(new Quiz(quizzes.getJSONObject(i)));
            }
        }

        Calendar now = Calendar.getInstance();
        int m = now.get(Calendar.MONTH);
        for(int i = 0; i < 6 ; i++){
            calendarOutlook.addElement(new CalendarMonth(m,this));
            m++;
        }

        /** Modifying JSON fields to work with parser, errors result if does not start with capital character. */
        c = CanvasAPI.getCourseAnnouncements(token, id).toCharArray();
        for (int i = 0; i < c.length; i++) {
            char a = '\"';
            if (a == c[i]) {
                c[i + 1] = Character.toUpperCase(c[i + 1]);
            }
        }
        d = new String(c);

        if (d.length() > 0){
            JSONArray announcements = new JSONArray(d);

            for(int i = 0; i < announcements.length(); i++){
                announcementList.addElement(new Announcement(announcements.getJSONObject(i)));
            }
        }
        token = "";
    }

    public void start(){
        thread = new Thread(this);
        thread.start();
    }

    public double getId(){ return id;}
    public void setId(int i){id = i;}

    public String getName(){return name;}
    public void setName(String n){name = n;}

    public String getCourseCode(){return courseCode;}
    public void setCourseCode(String c){courseCode = c;}

    public boolean restricted(){return restricted;}

    public Vector<Assignment> getAssignmentList(){return assignmentList;}

    public Vector<Quiz> getQuizList(){return quizList;}

    public Vector<CalendarMonth> getCalendarOutlook() {return calendarOutlook;}

    public Vector<Announcement> getAnnouncementList() {return announcementList;}
}

/* SAMPLE course from API

{
  //the unique identifier for the course
  "id": 370663,
  //the SIS identifier for the course, if defined. This field is only included if
  //the user has permission to view SIS information.
  "sis_course_id": null,
  //the integration identifier for the course, if defined. This field is only
  //included if the user has permission to view SIS information.
  "integration_id": null,
  //the full name of the course
  "name": "InstructureCon 2012",
  //the course code
  "course_code": "INSTCON12",
  //the current state of the course one of 'unpublished', 'available', 'completed',
  //or 'deleted'
  "workflow_state": "available",
  //the account associated with the course
  "account_id": 81259,
  //the root account associated with the course
  "root_account_id": 81259,
  //the enrollment term associated with the course
  "enrollment_term_id": 34,
  //the grading standard associated with the course
  "grading_standard_id": 25,
  //the start date for the course, if applicable
  "start_at": "2012-06-01T00:00:00-06:00",
  //the end date for the course, if applicable
  "end_at": "2012-09-01T00:00:00-06:00",
  //A list of enrollments linking the current user to the course. for student
  //enrollments, grading information may be included if include[]=total_scores
  "enrollments": null,
  //optional: the total number of active and invited students in the course
  "total_students": 32,
  //course calendar
  "calendar": null,
  //the type of page that users will see when they first visit the course - 'feed':
  //Recent Activity Dashboard - 'wiki': Wiki Front Page - 'modules': Course
  //Modules/Sections Page - 'assignments': Course Assignments List - 'syllabus':
  //Course Syllabus Page other types may be added in the future
  "default_view": "feed",
  //optional: user-generated HTML for the course syllabus
  "syllabus_body": "<p>syllabus html goes here</p>",
  //optional: the number of submissions needing grading returned only if the current
  //user has grading rights and include[]=needs_grading_count
  "needs_grading_count": 17,
  //optional: the enrollment term object for the course returned only if
  //include[]=term
  "term": null,
  //optional (beta): information on progress through the course returned only if
  //include[]=course_progress
  "course_progress": null,
  //weight final grade based on assignment group percentages
  "apply_assignment_group_weights": true,
  //optional: the permissions the user has for the course. returned only for a
  //single course and include[]=permissions
  "permissions": {"create_discussion_topic":true,"create_announcement":true},
  "is_public": true,
  "is_public_to_auth_users": true,
  "public_syllabus": true,
  "public_description": "Come one, come all to InstructureCon 2012!",
  "storage_quota_mb": 5,
  "storage_quota_used_mb": 5,
  "hide_final_grades": false,
  "license": "Creative Commons",
  "allow_student_assignment_edits": false,
  "allow_wiki_comments": false,
  "allow_student_forum_attachments": false,
  "open_enrollment": true,
  "self_enrollment": false,
  "restrict_enrollments_to_course_dates": false,
  "course_format": "online",
  //optional: this will be true if this user is currently prevented from viewing the
  //course because of date restriction settings
  "access_restricted_by_date": false
}

 */