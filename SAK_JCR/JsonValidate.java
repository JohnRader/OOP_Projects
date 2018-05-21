/*
Author: John Rader
Validates the list of JSON files to ensure they meet qualifications.
*/

import java.util.ArrayList;
import java.net.*;
import java.io.*;

public class JsonValidate {
    private String requestURL;
    protected ArrayList<String> urlContent;

    //default constructor
    JsonValidate() {
        requestURL = "";
        urlContent = new ArrayList<String>();
    }

    //readURL function that reads URL and connects if valid. 
    //reads each lines and adds it to the urlContent arrayList
    //has an exception catch if URL is not valid
    public Boolean checkURL(String urlIn) {
        requestURL = urlIn +"\n";
        Boolean returnValue = false; 
        try {
            URL myURL = new URL(requestURL);
            URLConnection myConnection = myURL.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                urlContent.add(inputLine);
                returnValue = true;
            }
            in.close();
        }
        catch (Exception e) {
            returnValue = false;
            System.out.println("There was an exception with the HttpRequest. Please enter a valid URL.");
            System.out.println("URL:" + requestURL);

        }
        return returnValue;
    }
    // reads every line in urlContent 
    public String toString() {
        String returnValue = "URL: " + requestURL;
        for (String s : urlContent) {
            returnValue = returnValue + s + "\n";
        } 
        System.out.println("JSON file contains firstName: " + returnValue.contains("firstName"));
        System.out.println("JSON file contains lastName: " + returnValue.contains("lastName"));
        System.out.println("JSON file contains preferredName: " + returnValue.contains("preferredName"));
        System.out.println("JSON file contains email: " + returnValue.contains("email"));
        System.out.println("JSON file contains city: " + returnValue.contains("city"));
        System.out.println("JSON file contains state: " + returnValue.contains("state"));
        System.out.println("JSON file contains favoriteHobby: " + returnValue.contains("favoriteHobby"));
        System.out.println("");
        return returnValue;
    }

   
}
    



