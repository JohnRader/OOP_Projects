/*
Author: John Rader
HttpRequestFile Class
*/

import java.util.ArrayList;
import java.io.*;

//main class
public class HttpRequestFile extends Thread{
    private String myFile;
    private ArrayList<String> myUrlList;
    


    //default constructor
    HttpRequestFile(String threadIn) {
        myFile = "";
        myUrlList = new ArrayList<String>();
    }

    /*this opens the file and reads the contents
    The file is broken up into different elements using the .split arugment
    if the first element in the line is http, then it is added to the list
    */
    public Boolean readFileURLs(String myFileIn) {
        Boolean returnValue = false; 
        myFile = myFileIn;
        try {
            BufferedReader buffRead = new BufferedReader(new FileReader(myFile));
            String line;
            while ((line = buffRead.readLine()) != null) {
                String[] elements = line.split("\"");
                if (elements.length > 1) {
                    if (elements[1].indexOf("http") > -1) {
                        myUrlList.add(elements[1]);
                        returnValue = true;
                    }
                }
            }
            buffRead.close();
        }
        catch (IOException e) {
            returnValue = false;
            System.out.println("There was an exception with the request. Please enter a valid file.");
        }
        
        return returnValue;
    }
    //takes in the URLs from the .json file and creates a HttpRequest function call for each of them
    public Boolean multipleHttpRequests () {
        Boolean returnValue = true;
        for (String s : myUrlList) {
            HttpRequest request = new HttpRequest();
            if (request.readURL(s)) {
                System.out.println(request);
                
            } else {
                returnValue = false;
            }
        }
        return returnValue; 
    }
    public Boolean validateJSONs () {
        Boolean returnValue = true;
        for (String s : myUrlList) {
            JsonValidate request = new JsonValidate();
            if (request.checkURL(s)) {
                System.out.println(request);
                
            } else {
                returnValue = false;
            }
        }
        return returnValue; 
    }

    //creates the string of the 
    public String toString() {
        String returnValue = "Entered File: " + myFile + "\n";
        for (String s : myUrlList) {
            returnValue = returnValue + s + "\n";
        } 
        return returnValue;
    }
}