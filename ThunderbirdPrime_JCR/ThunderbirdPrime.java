/*
@Author: John Rader
*/
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;
import java.util.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;


// Model Classes
class PersonInfo {
    private String PreferredName;
    private String SeatLocation;
    private String favoriteHobby;

    public PersonInfo() {
        PreferredName = "";
        SeatLocation = "";
        favoriteHobby = "";
    }

    public void LoadFromURL(String URLIn) throws Exception { 
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:ms");  
        LocalDateTime now = LocalDateTime.now(); 
        URL myURL = new URL(URLIn);
        System.out.println(dtf.format(now) +" "+myURL);
        ArrayList<String> myList = new ArrayList<String>();
        //HTTP request
        URLConnection myConnection = myURL.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            ParseLine(inputLine);
            System.out.println(inputLine);
        }
        in.close();
    }
    private void ParseLine(String line) {
        if (line.indexOf("PreferredName") > -1) {
            String[] elements = line.split("\"");
            PreferredName = elements[3];
        }
        if (line.indexOf("preferredName") > -1) {
            String[] elements = line.split("\"");
            PreferredName = elements[3];
        }
        if (line.indexOf("SeatLocation") > -1){
            String[] elements = line.split("\"");
            SeatLocation = elements[3];
        }
    }    

    public int GetRow() {
        String[] elements = SeatLocation.split("-");
        return Integer.parseInt(elements[1]);
    }
    public int GetColumn() {
        String[] elements = SeatLocation.split("-");
        return Integer.parseInt(elements[0]);
    }
    public String toString() {
        return "PreferredName: "+PreferredName+ "\nSeatLocation: " +SeatLocation+
            "\nFavoriteHobby: " + favoriteHobby+ "\nColumn: "+GetColumn()+", Row: "+GetRow()+"\n";
        
    }   
}

class PersonView extends JPanel {
    private PersonInfo info;
    public PersonView() {
        info = null;
    }
    public PersonView(PersonInfo infoIn) { 
        info = infoIn;
        System.out.println(info);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        g.setColor(new Color(0,0,255));
        if (info == null) {
            g.setColor(new Color(0,0,0));
            g.fillRect(10, 10, panelWidth-30, panelHeight-30);
            g.setColor(new Color(0,0,255));
            g.drawRect(10, 10, panelWidth-30, panelHeight-30);
        }
        else {
            g.drawRect(10, 10, panelWidth-30, panelHeight-30);
            g.drawString("NAME", panelWidth/3, panelHeight/2);
        }
        

        //Todo: Draw PreferredName. 
    }
}

// View Classes
class TBLView extends JFrame {
    public TBLView(ArrayList<PersonInfo>personInfoList) {
        setBounds(200,200,1200,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        JPanel roomView = new JPanel();
        roomView.setLayout(new GridLayout(4,8));
        roomView.setBounds(200,200,1200,800);
        
        for(int i=1; i<33; i++) {
            PersonView view = new PersonView();
            for(PersonInfo pI:personInfoList) {
                int seatNumber = ConvertRowAndColToSeatNumber(pI.GetColumn(), pI.GetRow());
                if (i == seatNumber) {
                    view = new PersonView(pI);
                }    
            }
            roomView.add(view);
        }
        Container contentPane = getContentPane();
        contentPane.add(roomView);
    }
    private int ConvertRowAndColToSeatNumber(int col, int row) {
        return (32-(row*8+(7-col)));
    }
}

//
// Main Class
//
public class ThunderbirdPrime {
    public static void main(String[] args) throws Exception {
        int URLcount = 0;
        PrintWriter writer = new PrintWriter("Thunderbird.log", "UTF-8");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:ms");  
        LocalDateTime now = LocalDateTime.now();
        System.out.println("ThunderbirdPrime starting...");

        if (args.length != 1) {
            System.out.println("Error: This application requires the name of a file to be passed into the application.");
            System.exit(0);
        }

        //Arraylist of person info
        ArrayList<PersonInfo>personInfoList = new ArrayList<PersonInfo>();
        BufferedReader myBR = new BufferedReader(new FileReader(args[0]));
        String line;
        while((line = myBR.readLine()) != null) {
            String[] elements = line.split("\"");
            if (elements.length > 1) {
                if (elements[1].indexOf("http") > -1) {
                    // Initiate http request to retrieve remote JSON file.
                    PersonInfo info = new PersonInfo();                  
                    info.LoadFromURL(elements[1]);
                    URLcount = URLcount + 1;
                    writer.println(dtf.format(now) + " -"+URLcount+"- " +elements[1]);
                    personInfoList.add(info);
                }
            }
        }  

        writer.close();
        TBLView myView = new TBLView(personInfoList);
        myView.setVisible(true);
    }
}