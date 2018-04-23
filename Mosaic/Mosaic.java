/** Author: John Rader
 * This is the Mosaic assignment.
 * The program creates a 12 by 12 grid of shapes which are chosen at random. There are
 * letters inside of these shapes which are also chosen at random. There is
 * a randomize button that once pressed will reshuffle the shapes and their colors. 
 * There is also a second button which once pressed will change all of the shapes colors to blue. 
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPanel;
import java.util.Random;
import java.awt.*;

//This is the MosaicFrame
class MosaicFrame extends JFrame implements ActionListener {
    public ArrayList<ShapeTile> tileList;

    public MosaicFrame() {
        setBounds(25,25,1000,1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //panel for buttons
        JPanel buttonPanel = new JPanel();
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        //the buttons
        JButton randomize = new JButton("Randomize");
        randomize.addActionListener(this);
        JButton bluebutton = new JButton("Blue Button");
        bluebutton.addActionListener(new BlueButtonListener());
        //adds buttons to panel
        buttonPanel.add(randomize);
        buttonPanel.add(bluebutton);
 

        JPanel ShapePanel = new JPanel();
        contentPane.add(ShapePanel, BorderLayout.CENTER);
        ShapePanel.setLayout(new GridLayout(12,12));

        tileList = new ArrayList<ShapeTile>();
        for(int i=0; i<144; i++){
            ShapeTile tile = new ShapeTile();
            ShapePanel.add(tile);
            tileList.add(tile);
        }
    }
    //randomize button action listener
   public void actionPerformed(ActionEvent e) {
        for (ShapeTile tile:tileList) {
            tile.SetRandomValue();
        }
        repaint();
        System.out.println("");
        System.out.println("Start Paint***");     
    }

    //blue buttons action listener
    class BlueButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (ShapeTile tile:tileList) {
                tile.allBlue();
            }
            repaint();
            System.out.println("");
            System.out.println("Start Paint***");
    }
}
}

//This creates the shape panels
class ShapeTile extends JPanel{
    private int red;
    private int blue;
    private int green;
    private char letter;

    ShapeTile(){
        super();
        SetRandomValue();
    }

    final public void allBlue(){
        red = 0;
        green = 0;
        blue = 255;
    }
    final public void SetRandomValue(){
       red = GetNumberBetween(0,255);
       green = GetNumberBetween(0,255);
       blue = GetNumberBetween(0,255); 
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int shapeWidth = getWidth();
        int shapeHeight = getHeight();

        //sets the shapes color randomly
        g.setColor(new Color(red,green,blue));

        //This chooses the shape
        if (GetNumberBetween(0,1) == 1)
            g.fillRect(0, 0, shapeWidth, shapeHeight);
        else 
            g.fillOval(0, 0, shapeWidth, shapeHeight);

        //initializes the font inside of the shape and sets its color randomly as well
        int letterX = (shapeWidth/2 - 5);
        int letterY = (shapeHeight/2 + 3);
        final int fontSize=20;
        g.setColor(new Color(GetNewColor(red),GetNewColor(green),GetNewColor(blue)));
        g.setFont(new Font("Arial", Font.PLAIN, fontSize));
        
      
        //Gets random letters using ascii, converts the Char to a string after putting it through the GetNumberBetween function
        letter = GetNumberBetween(65, 90);
        g.drawString(String.valueOf(letter),letterX,letterY);
        System.out.print(letter);
    }
    //this function randomizes the color given
    private static int GetNewColor(int colorIn) {
        return ((colorIn+128)%256);
    }
    //this function is our simple random number function slightly altered to allow for random letters
    private static char GetNumberBetween(int min, int max) {
        Random myRandom = new Random();
        return (char)(min + myRandom.nextInt(max-min+1));
    }
}
//main function
public class Mosaic{
    public static void main(String[] args) {
        System.out.println("Mosaic is starting...");
        MosaicFrame myMosaicFrame = new MosaicFrame();
        myMosaicFrame.setVisible(true);
    }
}
