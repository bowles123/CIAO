package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CanvasAPI {
	
	public static String getAllCourses(String accessToken)
	{
		String courses = "";
        try {
            StringBuilder sc = new StringBuilder();
            String address = "https://usu.instructure.com/api/v1/courses?access_token=" + accessToken;
            URL url = new URL(address);
            
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            BufferedReader bd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = bd.readLine();
            
            while (line != null) {
                sc.append(line);
                sc.append(System.lineSeparator());
                line = bd.readLine();
            }
            
            courses = sc.toString();
            bd.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
	}
	
	public static String getFavoriteCourses(String accessToken)
	{
		String courses = "";
        try {
            StringBuilder sc = new StringBuilder();
            String address = "https://usu.instructure.com/api/v1/users/self/favorites/courses?access_token=" + accessToken;
            URL url = new URL(address);
            
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            BufferedReader bd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = bd.readLine();
            
            while (line != null) {
                sc.append(line);
                sc.append(System.lineSeparator());
                line = bd.readLine();
            }
            
            courses = sc.toString();
            bd.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
	}
	
	public static String getUserInfo(String accessToken)
	{
		String userInfo = "";
        try {
            StringBuilder sc = new StringBuilder();
            String address = "https://usu.instructure.com/api/v1/users/self?access_token=" + accessToken;
            URL url = new URL(address);
            
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            BufferedReader bd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = bd.readLine();
            
            while (line != null) {
                sc.append(line);
                sc.append(System.lineSeparator());
                line = bd.readLine();
            }
            
            userInfo = sc.toString();
            bd.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfo;
	}
	
	public static String getCourseAssignments(String accessToken, long courseId)
	{
		String assignments = "";
        try {
            StringBuilder sc = new StringBuilder();
            String address = "https://usu.instructure.com/api/v1/courses/" + courseId + "/assignments?access_token=" + accessToken + "&per_page=50";
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader bd = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line = bd.readLine();
            while (line != null) {
                sc.append(line);
                sc.append(System.lineSeparator());
                line = bd.readLine();
            }
            assignments = sc.toString();
            bd.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return assignments;
	}
	
	public static String getCourseAnnouncements(String accessToken, long courseId)
	{
		String announcements = "";
        try {
            StringBuilder sc = new StringBuilder();
            String address = "https://usu.instructure.com/api/v1/courses/" + courseId;
			address += "/discussion_topics?access_token=" + accessToken + "&only_announcements=true&per_page=50";
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
	            BufferedReader bd = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
	            String line = bd.readLine();
	            while (line != null) {
	                sc.append(line);
	                sc.append(System.lineSeparator());
	                line = bd.readLine();
	            }
	            announcements = sc.toString();
	            bd.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return announcements;
	}
	
	public static String getCourseQuizzes(String accessToken, long courseId)
	{
		String quizzes = "";
        try {
            StringBuilder sc = new StringBuilder();
            String address = "https://usu.instructure.com/api/v1/courses/" + courseId + "/quizzes?access_token=" + accessToken + "&per_page=50";
            URL url = new URL(address);
            
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            
            if (connection.getResponseCode() != HttpURLConnection.HTTP_NOT_FOUND) {
                BufferedReader bd = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = bd.readLine();
                
                while (line != null) {
                    sc.append(line);
                    sc.append(System.lineSeparator());
                    line = bd.readLine();
                }
                
                quizzes = sc.toString();
                bd.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }       
        return quizzes;
	}
}
