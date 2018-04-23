/*
Author: John Rader
HttpRequest Class
*/
import java.util.ArrayList;
import java.net.*;
import java.io.*;

public class HttpRequest {
    private String requestURL;
    protected ArrayList<String> urlContent;
    HttpRequest() {
        requestURL = "";
        urlContent = new ArrayList<String>();
    }

    //readURL function that reads URL and connects if valid. 
    //reads each lines and adds it to the urlContent arrayList
    //has an exception catch if URL is not valid
    public Boolean readURL(String urlIn) {
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
    // reads every line in urlContent and 
    public String toString() {
        String returnValue = "URL: " + requestURL;
        for (String s : urlContent) {
            returnValue = returnValue + s + "\n";
        } 
        return returnValue;
    }
}