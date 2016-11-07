package database;

import org.json.JSONObject;
import java.util.Calendar;

/**
 * Holds all information on an assignment.
 */

public class Assignment {
    private String name;
    private String description;
    private Calendar dueDate;
    private String htmlURL;
    private String gradingType;
    private boolean muted;
    private Calendar updatedDate;
    private double pointsPossible;

    public Assignment(String name, String desc) {
    	this.name = name;
    	this.description = desc;
    	htmlURL = null;
    	gradingType = null;
    	pointsPossible = 10.0;
    }

    public Assignment(JSONObject a){
        name = a.getString("Name");
        Object object = a.get("Description");
        if(object instanceof String) {
            description = ((String) object).replaceAll("\\<.*?>","");
        } else {
            description = null;
        }
        object = a.get("Due_at");
        if(object instanceof String) {
            dueDate = Utility.parseDate((String)object);
        } else {
            dueDate = null;
        }
        object = a.get("Html_url");
        if(object instanceof String) {
            htmlURL = (String)object;
        } else {
            htmlURL = null;
        }
        object = a.get("Grading_type");
        if(object instanceof String) {
            gradingType = (String)object;
        } else {
            gradingType = null;
        }
        object = a.get("Updated_at");
        if(object instanceof String) {
            updatedDate = Utility.parseDate((String)object);
        } else {
            updatedDate = null;
        }
        muted = a.getBoolean("Muted");
        if (!a.isNull("Points_possible")) {
            pointsPossible = a.getDouble("Points_possible");
        }
    }

    public String getName(){
        return name;
    }
    public void setName(String n){name = n;}

    public String getDescription(){
        return description;
    }
    public void setDescription(String d){description = d;}

    public Calendar getDueAt(){return dueDate;}
    public void setDueAt(Calendar s){dueDate = s;}

    public String getHtmlURL(){
        return htmlURL;
    }
    public void setHtmlURL(String h){htmlURL = h;}

    public double getPointsPossible(){
        return pointsPossible;
    }
    public void setPointsPossible(Float p){pointsPossible = p;}

    public String getGradingType(){return gradingType;}
    public void setGradingType(String g){gradingType = g;}

    public boolean getMuted(){return muted;}
    public void setMuted(boolean m){muted = m;}

    public Calendar getDueDate(){return dueDate;}
    public void setDueDate(Calendar s){dueDate = s;}

    public Calendar getUpdatedDate(){return updatedDate;}
    public void setUpdatedDate(Calendar s){updatedDate = s;}

}

/* SAMPLE assignment from API

{
  //the ID of the assignment
  "id": 4,
  //the name of the assignment
  "name": "some assignment",
  //the assignment description, in an HTML fragment
  "description": "<p>Do the following:</p>...",
  //The time at which this assignment was originally created
  "created_at": "2012-07-01T23:59:00-06:00",
  //The time at which this assignment was last modified in any way
  "updated_at": "2012-07-01T23:59:00-06:00",
  //the due date for the assignment. returns null if not present. NOTE: If this
  //assignment has assignment overrides, this field will be the due date as it
  //applies to the user requesting information from the API.
  "due_at": "2012-07-01T23:59:00-06:00",
  //the lock date (assignment is locked after this date). returns null if not
  //present. NOTE: If this assignment has assignment overrides, this field will be
  //the lock date as it applies to the user requesting information from the API.
  "lock_at": "2012-07-01T23:59:00-06:00",
  //the unlock date (assignment is unlocked after this date) returns null if not
  //present NOTE: If this assignment has assignment overrides, this field will be
  //the unlock date as it applies to the user requesting information from the API.
  "unlock_at": "2012-07-01T23:59:00-06:00",
  //whether this assignment has overrides
  "has_overrides": true,
  //(Optional) all dates associated with the assignment, if applicable
  "all_dates": null,
  //the ID of the course the assignment belongs to
  "course_id": 123,
  //the URL to the assignment's web page
  "html_url": "https://...",
  //the URL to download all submissions as a zip
  "submissions_download_url": "https://example.com/courses/:course_id/assignments/:id/submissions?zip=1",
  //the ID of the assignment's group
  "assignment_group_id": 2,
  //Allowed file extensions, which take effect if submission_types includes
  //'online_upload'.
  "allowed_extensions": ["docx", "ppt"],
  //Boolean flag indicating whether or not Turnitin has been enabled for the
  //assignment. NOTE: This flag will not appear unless your account has the Turnitin
  //plugin available
  "turnitin_enabled": true,
  //Settings to pass along to turnitin to control what kinds of matches should be
  //considered. originality_report_visibility can be 'immediate', 'after_grading',
  //'after_due_date', or 'never' exclude_small_matches_type can be null, 'percent',
  //'words' exclude_small_matches_value: - if type is null, this will be null also -
  //if type is 'percent', this will be a number between 0 and 100 representing match
  //size to exclude as a percentage of the document size. - if type is 'words', this
  //will be number > 0 representing how many words a match must contain for it to be
  //considered NOTE: This flag will not appear unless your account has the Turnitin
  //plugin available
  "turnitin_settings": null,
  //If this is a group assignment, boolean flag indicating whether or not students
  //will be graded individually.
  "grade_group_students_individually": false,
  //(Optional) assignment's settings for external tools if submission_types include
  //'external_tool'. Only url and new_tab are included. Use the 'External Tools' API
  //if you need more information about an external tool.
  "external_tool_tag_attributes": null,
  //Boolean indicating if peer reviews are required for this assignment
  "peer_reviews": false,
  //Boolean indicating peer reviews are assigned automatically. If false, the
  //teacher is expected to manually assign peer reviews.
  "automatic_peer_reviews": false,
  //Integer representing the amount of reviews each user is assigned. NOTE: This key
  //is NOT present unless you have automatic_peer_reviews set to true.
  "peer_review_count": 0,
  //String representing a date the reviews are due by. Must be a date that occurs
  //after the default due date. If blank, or date is not after the assignment's due
  //date, the assignment's due date will be used. NOTE: This key is NOT present
  //unless you have automatic_peer_reviews set to true.
  "peer_reviews_assign_at": "2012-07-01T23:59:00-06:00",
  //The ID of the assignment’s group set, if this is a group assignment. For group
  //discussions, set group_category_id on the discussion topic, not the linked
  //assignment.
  "group_category_id": 1,
  //if the requesting user has grading rights, the number of submissions that need
  //grading.
  "needs_grading_count": 17,
  //if the requesting user has grading rights and the
  //'needs_grading_count_by_section' flag is specified, the number of submissions
  //that need grading split out by section. NOTE: This key is NOT present unless you
  //pass the 'needs_grading_count_by_section' argument as true.  ANOTHER NOTE: it's
  //possible to be enrolled in multiple sections, and if a student is setup that way
  //they will show an assignment that needs grading in multiple sections
  //(effectively the count will be duplicated between sections)
  "needs_grading_count_by_section": [{"section_id":"123456","needs_grading_count":5}, {"section_id":"654321","needs_grading_count":0}],
  //the sorting order of the assignment in the group
  "position": 1,
  //(optional, present if Post Grades to SIS feature is enabled)
  "post_to_sis": true,
  //(optional, Third Party unique identifier for Assignment)
  "integration_id": "12341234",
  //(optional, Third Party integration data for assignment)
  "integration_data": "12341234",
  //whether the assignment is muted
  "muted": null,
  //the maximum points possible for the assignment
  "points_possible": 12,
  //the types of submissions allowed for this assignment list containing one or more
  //of the following: 'discussion_topic', 'online_quiz', 'on_paper', 'none',
  //'external_tool', 'online_text_entry', 'online_url', 'online_upload'
  //'media_recording'
  "submission_types": ["online_text_entry"],
  //The type of grading the assignment receives; one of 'pass_fail', 'percent',
  //'letter_grade', 'gpa_scale', 'points'
  "grading_type": "points",
  //The id of the grading standard being applied to this assignment. Valid if
  //grading_type is 'letter_grade' or 'gpa_scale'.
  "grading_standard_id": null,
  //Whether the assignment is published
  "published": true,
  //Whether the assignment's 'published' state can be changed to false. Will be
  //false if there are student submissions for the assignment.
  "unpublishable": false,
  //Whether the assignment is only visible to overrides.
  "only_visible_to_overrides": false,
  //Whether or not this is locked for the user.
  "locked_for_user": false,
  //(Optional) Information for the user about the lock. Present when locked_for_user
  //is true.
  "lock_info": null,
  //(Optional) An explanation of why this is locked for the user. Present when
  //locked_for_user is true.
  "lock_explanation": "This assignment is locked until September 1 at 12:00am",
  //(Optional) id of the associated quiz (applies only when submission_types is
  //['online_quiz'])
  "quiz_id": 620,
  //(Optional) whether anonymous submissions are accepted (applies only to quiz
  //assignments)
  "anonymous_submissions": false,
  //(Optional) the DiscussionTopic associated with the assignment, if applicable
  "discussion_topic": null,
  //(Optional) Boolean indicating if assignment will be frozen when it is copied.
  //NOTE: This field will only be present if the AssignmentFreezer plugin is
  //available for your account.
  "freeze_on_copy": false,
  //(Optional) Boolean indicating if assignment is frozen for the calling user.
  //NOTE: This field will only be present if the AssignmentFreezer plugin is
  //available for your account.
  "frozen": false,
  //(Optional) Array of frozen attributes for the assignment. Only account
  //administrators currently have permission to change an attribute in this list.
  //Will be empty if no attributes are frozen for this assignment. Possible frozen
  //attributes are: title, description, lock_at, points_possible, grading_type,
  //submission_types, assignment_group_id, allowed_extensions, group_category_id,
  //notify_of_update, peer_reviews NOTE: This field will only be present if the
  //AssignmentFreezer plugin is available for your account.
  "frozen_attributes": ["title"],
  //(Optional) If 'submission' is included in the 'include' parameter, includes a
  //Submission object that represents the current user's (user who is requesting
  //information from the api) current submission for the assignment. See the
  //Submissions API for an example response. If the user does not have a submission,
  //this key will be absent.
  "submission": null,
  //(Optional) If true, the rubric is directly tied to grading the assignment.
  //Otherwise, it is only advisory. Included if there is an associated rubric.
  "use_rubric_for_grading": true,
  //(Optional) An object describing the basic attributes of the rubric, including
  //the point total. Included if there is an associated rubric.
  "rubric_settings": "{"points_possible"=>12}",
  //(Optional) A list of scoring criteria and ratings for each rubric criterion.
  //Included if there is an associated rubric.
  "rubric": null,
  //(Optional) If 'assignment_visibility' is included in the 'include' parameter,
  //includes an array of student IDs who can see this assignment.
  "assignment_visibility": [137, 381, 572],
  //(Optional) If 'overrides' is included in the 'include' parameter, includes an
  //array of assignment override objects.
  "overrides": null
}

 */