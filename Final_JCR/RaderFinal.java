/*
Final Project
@Author: John Rader
*/

import java.util.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



class JsonInfo{
    private String ButtonName;
    private String name;

    public JsonInfo() {
        ButtonName = "";
    }

    public String ParseName(String line) throws Exception {
        if (line.indexOf("PreferredName")> -1) {
            String[] elements = line.split("\"");
            ButtonName = elements[3];
        }
        if (line.indexOf("preferredName") > -1) {
            String[] elements = line.split("\"");
            ButtonName = elements[3];
        }
        //return the buttonname for future use
        return ButtonName;
    }

    //This takes in the URL and creates the http request
    public String LoadFromURL (String URLIn) throws Exception { 
        URL myURL = new URL(URLIn);
        ArrayList<String> infoList = new ArrayList<String>();
        System.out.println(myURL);
        //HTTP request
        URLConnection myConnection = myURL.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            name = ParseName(inputLine);
            infoList.add(inputLine);
        }
        in.close();
        //this returns the button name created with the ParseName method
        return name;
    }
}

class MyFrame extends JFrame {
    public MyFrame(){
    }
  
    public MyFrame(ArrayList<String> names) throws Exception{
        setBounds(200,200,1200,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        contentPane.add(buttonPanel, BorderLayout.CENTER);

        for (String s : names) {
            buttonPanel.add(CreateButton(s));  
        }
    }

    public JButton CreateButton(String name) {
        JButton b1 = new JButton(name);
        b1.addActionListener(new ActionListener(){ 
            public void actionPerformed(ActionEvent e) {
                System.out.println("This is supposed to be the Json data for each name.");
            }
        });
        return b1;
    }
}

//main class
public class RaderFinal {
    public static void main(String[] args)  throws Exception{
        System.out.println("\nWelcome to John's Final Project!");
        System.out.println("Gathering information from the following URLs...\n");
        
        //list of names we are using for the buttons
        ArrayList<String> nameList = new ArrayList<String>();
        if (args.length != 1) {
            System.out.println("Error: This application requires the name of a file to be passed into the application.");
            System.exit(0);
        }

        //list of info from the json files
        ArrayList<JsonInfo>jsonInfos = new ArrayList<JsonInfo>();
        BufferedReader myBR = new BufferedReader(new FileReader(args[0]));
        String line;

        while((line = myBR.readLine()) != null) {
            String[] elements = line.split("\"");
            if (elements.length > 1) {
                if (elements[1].indexOf("http") > -1) {
                    JsonInfo info = new JsonInfo();    
                    nameList.add(info.LoadFromURL(elements[1]));
                    //jsonInfos.add(info);
                    //System.out.println(jsonInfos.add(info));
                    }
                }
            }

            MyFrame myFrame = new MyFrame(nameList); 
            myFrame.setVisible(true); 
            System.out.println("\nHere are the names gathered from the URLs:\n");
            System.out.println(nameList);

        myBR.close();
    }
}


   