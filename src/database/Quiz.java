package database;

import org.json.JSONObject;

import java.util.Calendar;

/**
 * Holds all information on a quiz.
 */


public class Quiz {
    private String title;
    private String htmlURL;
    private String description;
    private int timeLimit;
    private String scoringPolicy;
    private int allowedAttempts;
    private double questionCount;
    private double pointsPossible;
    private Calendar dueDate;

    public Quiz(String title, String desc) {
    	this.title = title;
    	this.description = desc;
    	htmlURL = null;
    	timeLimit = 1;
    	scoringPolicy = null;
    	allowedAttempts = 2;
    	questionCount = 10;
    	pointsPossible = 10;
    }
    
    public Quiz(JSONObject q){
    	try {
        title = q.getString("Title");
        htmlURL = q.getString("Html_url");
        description = q.getString("Description").replaceAll("\\<.*?>","");
    	} catch (Exception e) {
    		
    	}
    	
    	try {
            timeLimit = q.getInt("Time_limit");
            pointsPossible = q.getDouble("Points_possible");
    	} catch (Exception e) {
    		
    	}
    	
    	try {
        scoringPolicy = q.getString("Scoring_policy");
        allowedAttempts = q.getInt("Allowed_attempts");
        questionCount = q.getDouble("Question_count");
        dueDate = Utility.parseDate(q.getString("Due_at"));
    	} catch (Exception e) {
    		
    	}
    }

    public String getTitle(){return title;}
    public void setTitle(String t){title = t;}

    public String getHtmlURL(){
        return htmlURL;
    }
    public void setHtmlURL(String h){htmlURL = h;}

    public String getDescription(){
        return description;
    }
    public void setDescription(String d){description = d;}

    public int getTimeLimit(){return timeLimit;}
    public void setTimeLimit(int t){timeLimit = t;}

    public String getScoringPolicy(){return scoringPolicy;}
    public void setScoringPolicy(String s){scoringPolicy = s;}

    public int getAllowedAttempts(){
        return allowedAttempts;
    }
    public void setAllowedAttempts(int a){allowedAttempts = a;}

    public double getQuestionCount(){
        return questionCount;
    }
    public void setQuestionCount(double q){questionCount = q;}

    public double getPointsPossible(){
        return pointsPossible;
    }
    public void setPointsPossible(double p){pointsPossible = p;}

    public Calendar getDueDate(){return dueDate;}
    public void setDueDate(Calendar s){dueDate = s;}
}

/* SAMPLE quiz from API

{
  //the ID of the quiz
  "id": 5,
  //the title of the quiz
  "title": "Hamlet Act 3 Quiz",
  //the HTTP/HTTPS URL to the quiz
  "html_url": "http://canvas.example.edu/courses/1/quizzes/2",
  //a url suitable for loading the quiz in a mobile webview.  it will persiste the
  //headless session and, for quizzes in public courses, will force the user to
  //login
  "mobile_url": "http://canvas.example.edu/courses/1/quizzes/2?persist_healdess=1&force_user=1",
  //A url that can be visited in the browser with a POST request to preview a quiz
  //as the teacher. Only present when the user may grade
  "preview_url": "http://canvas.example.edu/courses/1/quizzes/2/take?preview=1",
  //the description of the quiz
  "description": "This is a quiz on Act 3 of Hamlet",
  //type of quiz possible values: 'practice_quiz', 'assignment', 'graded_survey',
  //'survey'
  "quiz_type": "assignment",
  //the ID of the quiz's assignment group:
  "assignment_group_id": 3,
  //quiz time limit in minutes
  "time_limit": 5,
  //shuffle answers for students?
  "shuffle_answers": false,
  //let students see their quiz responses? possible values: null, 'always',
  //'until_after_last_attempt'
  "hide_results": "always",
  //show which answers were correct when results are shown? only valid if
  //hide_results=null
  "show_correct_answers": true,
  //restrict the show_correct_answers option above to apply only to the last
  //submitted attempt of a quiz that allows multiple attempts. only valid if
  //show_correct_answers=true and allowed_attempts > 1
  "show_correct_answers_last_attempt": true,
  //when should the correct answers be visible by students? only valid if
  //show_correct_answers=true
  "show_correct_answers_at": "2013-01-23T23:59:00-07:00",
  //prevent the students from seeing correct answers after the specified date has
  //passed. only valid if show_correct_answers=true
  "hide_correct_answers_at": "2013-01-23T23:59:00-07:00",
  //prevent the students from seeing their results more than once (right after they
  //submit the quiz)
  "one_time_results": true,
  //which quiz score to keep (only if allowed_attempts != 1) possible values:
  //'keep_highest', 'keep_latest'
  "scoring_policy": "keep_highest",
  //how many times a student can take the quiz -1 = unlimited attempts
  "allowed_attempts": 3,
  //show one question at a time?
  "one_question_at_a_time": false,
  //the number of questions in the quiz
  "question_count": 12,
  //The total point value given to the quiz
  "points_possible": 20,
  //lock questions after answering? only valid if one_question_at_a_time=true
  "cant_go_back": false,
  //access code to restrict quiz access
  "access_code": "2beornot2be",
  //IP address or range that quiz access is limited to
  "ip_filter": "123.123.123.123",
  //when the quiz is due
  "due_at": "2013-01-23T23:59:00-07:00",
  //when to lock the quiz
  "lock_at": null,
  //when to unlock the quiz
  "unlock_at": "2013-01-21T23:59:00-07:00",
  //whether the quiz has a published or unpublished draft state.
  "published": true,
  //Whether the assignment's 'published' state can be changed to false. Will be
  //false if there are student submissions for the quiz.
  "unpublishable": true,
  //Whether or not this is locked for the user.
  "locked_for_user": false,
  //(Optional) Information for the user about the lock. Present when locked_for_user
  //is true.
  "lock_info": null,
  //(Optional) An explanation of why this is locked for the user. Present when
  //locked_for_user is true.
  "lock_explanation": "This quiz is locked until September 1 at 12:00am",
  //Link to Speed Grader for this quiz. Will not be present if quiz is unpublished
  "speedgrader_url": "http://canvas.instructure.com/courses/1/speed_grader?assignment_id=1",
  //Link to endpoint to send extensions for this quiz.
  "quiz_extensions_url": "http://canvas.instructure.com/courses/1/quizzes/2/quiz_extensions",
  //Permissions the user has for the quiz
  "permissions": null,
  //list of due dates for the quiz
  "all_dates": null,
  //Current version number of the quiz
  "version_number": 3,
  //List of question types in the quiz
  "question_types": ["mutliple_choice", "essay"]
}

 */