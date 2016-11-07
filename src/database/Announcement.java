package database;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Calendar;

/**
 * Holds all information on an announcement.
 */
public class Announcement {
    private int id;
    private String title;
    private Calendar postDate;
    private String author;
    private String message;
    private String htmlURL;

    public Announcement(JSONObject a){
        title = a.getString("Title");
        Object object = a.getInt("Id");
        if(object instanceof String) {
            id = (int)object;
        } else {
            id = 0;
        }
        object = a.get("Message");
        if(object instanceof String) {
            message = ((String) object).replaceAll("\\<.*?>","");
        } else {
            message = null;
        }
        object = a.get("Html_url");
        if(object instanceof String) {
            htmlURL = (String)object;
        } else {
            htmlURL = null;
        }
        try {
        	object = a.getString("User_name");
        }
        catch(JSONException e) {
        	e.printStackTrace();
        }
        if (object instanceof String) {
            author = (String)object;
        } else {
            author = null;
        }
        object = a.getString("Posted_at");
        if (object instanceof String) {
            postDate = Utility.parseDate((String)object);
        } else {
            postDate = null;
        }
    }
    
    public String getTitle() {return title;}
    public Calendar getDate() {return postDate;}
    public String getMessage() {return message;}
    public int getId() {return id;}
    public String getAuthor(){return author;}
    public String getHtmlURL(){return htmlURL;}
}

/*
while(1);[{"id":1325920,
"title":"Monday class",
"last_reply_at":"2016-02-29T23:59:04Z",
"delayed_post_at":null,
"posted_at":"2016-02-29T23:59:04Z",
"assignment_id":null,
"root_topic_id":null,
"position":1,
"podcast_has_student_posts":false,
"discussion_type":"side_comment",
"lock_at":null,
"allow_rating":false,
"only_graders_can_rate":false,
"sort_by_rating":false,
"user_name":"Shantanu Saxena",
"discussion_subentry_count":0,
"permissions":{"attach":false,"update":false,"delete":false},
"require_initial_post":null,"user_can_see_posts":true,
"podcast_url":null,
"read_state":"read",
"unread_count":0,
"subscribed":false,
"topic_children":[],
"attachments":[],
"published":true,
"can_unpublish":false,
"locked":false,
"can_lock":true,

"author":{"id":1309084,
"display_name":"Shantanu Saxena",
"avatar_image_url":"https://usu.instructure.com/images/thumbnails/53619513/j4WwgDFpB0RHet7QCLkyaZtn9LMqY3Gr4RzGAIkW",
"html_url":"https://usu.instructure.com/courses/393931/users/1309084"},
"html_url":"https://usu.instructure.com/courses/393931/discussion_topics/1325920",
"url":"https://usu.instructure.com/courses/393931/discussion_topics/1325920",
"pinned":false,
"group_category_id":null,
"can_group":false,
"message":"\u003Cp\u003EHello Everyone, \u003C/p\u003E\r\n\u003Cp\u003EI was not able to take the class at 02:30pm today due to some unavoidable circumstances. I am sorry for the inconvenience it might had caused. We will be having the class as usual on Wednesday.\u003C/p\u003E\r\n\u003Cp\u003E \u003C/p\u003E\r\n\u003Cp\u003EShantanu Saxena\u003C/p\u003E\r\n\u003Cp\u003EGTA- CS 3450\u003C/p\u003E",
"subscription_hold":"topic_is_announcement","locked_for_user":false}]
 */