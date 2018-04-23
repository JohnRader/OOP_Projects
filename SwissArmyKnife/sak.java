/* 
Author: John Rader
Swiss Army Knife Application
*/

public class sak {
    public static void main(String[] args) {
        System.out.println("");
        // If no arguments are entered
        if (args.length < 1) {
            System.out.println("This application requires at least one argument. Use the \"-Help\" parameter for more"
             +"information on how to use the application.");
        } 
        //if -help is entered
        else if (args[0].equalsIgnoreCase("-Help")) {
            Help.printHelp();          
        } 
        //if -HttpRequest is entered
        else if (args[0].equalsIgnoreCase("-HttpRequest")) {
                String URL = args[1];
                HttpRequest request = new HttpRequest();
                if (request.readURL(URL)) {
                    System.out.println(request);         
            }
        }
        //if -HttpRequestfile is entered
        else if (args[0].equalsIgnoreCase("-HttpRequestFile")) {    
                String fileName = args[1];
                HttpRequestFile requestFile = new HttpRequestFile();
                if (requestFile.readFileURLs(fileName)) {
                    System.out.println(requestFile);
                    requestFile.multipleHttpRequests();      
            }
        }       
        //if -HttpRequestMultiThreaded is entered
        else if (args[0].equalsIgnoreCase("-HttpRequestMultiThreaded")) {
             System.out.println("This feature is going to be added at a later date.");  
        }       
    }
} 