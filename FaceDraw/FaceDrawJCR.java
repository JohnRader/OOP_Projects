import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;
import java.util.Random;
//The self-explanatory OvalDraw class
class OvalDraw extends Oval {
    public OvalDraw() {
        super(0, 0, 0, 0);
    }
    public OvalDraw (int positionXIn, int positionYIn, int widthIn, int heightIn) {
        super(positionXIn, positionYIn, widthIn, heightIn);
    }
    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.drawOval(getPositionX(), getPositionY(), getWidth(), getHeight());
    }
}
//This is the main Face class. Creates the head and eyes only. Extends the OvalDraw class for creating the ovals.
class Face extends OvalDraw{
    private OvalDraw eye1;
    private OvalDraw eye2;
    public Face() {
        super(0,0,0,0);
    }
    public Face(int positionXIn, int positionYIn, int widthIn, int heightIn) {
        super(positionXIn, positionYIn, widthIn, heightIn);
        
        int eyeWidth = widthIn / 6;
        int eyeHeight = heightIn / 6;
        int eyePositionX =   positionXIn + (widthIn / 3) - (eyeWidth / 2);
        int eyePositionY = positionYIn + (heightIn / 3) - (eyeHeight / 2);
        int eyePosX_2 = eyePositionX + (widthIn /3);

        eye1 = new OvalDraw(eyePositionX, eyePositionY, eyeWidth, eyeHeight); 
        eye2 = new OvalDraw(eyePosX_2, eyePositionY, eyeWidth, eyeHeight);
    }
     public void paintComponent(Graphics g) {
        super.paintComponent(g);
        eye1.paintComponent(g);
        eye2.paintComponent(g);
        System.out.format("Dimensions are: (x=%d, y=%d, w=%d, h=%d)\n", 
        getPositionX(), getPositionY(), getWidth(), getHeight());
    }
}
//Straight face that extends face altering only the smile status.
class StraightFace extends Face {
    public StraightFace() {
        super(0,0,0,0);
    }
    public StraightFace(int positionXIn, int positionYIn, int widthIn, int heightIn) {
        super(positionXIn, positionYIn, widthIn, heightIn);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(getPositionX() + (getWidth() / 3), getPositionY() + getHeight() - (getHeight() / 3), getPositionX() + getWidth() - (getWidth() / 3), getPositionY() + getHeight() - (getHeight() / 3));
    }
}
//Happy face that extends face altering only the smile status.
class HappyFace extends Face {
    public HappyFace() {
        super(0,0,0,0);
    }
    public HappyFace(int positionXIn, int positionYIn, int widthIn, int heightIn) {
        super(positionXIn, positionYIn, widthIn, heightIn);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawArc(getPositionX(), getPositionY() - (getHeight() / 3), getWidth(), getHeight(), -45, -90);
    }
}
//Sad face that extends face altering only the smile status.
class SadFace extends Face {
    public SadFace () {
        super(0,0,0,0);
    }
    public SadFace (int positionXIn, int positionYIn, int widthIn, int heightIn) {
        super(positionXIn, positionYIn, widthIn, heightIn);  
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawArc(getPositionX(), getPositionY() + (getHeight() / 2), getWidth(), getHeight(), 45, 90);
    }
}
//Creates the ArrayList for faces, generates a random number of faces each face has an equally random chance of being 
//either Sad, Happy, or Straight. These faces are added to the ArrayList, the paint component goes through each
//entry of the ArrayList and paints it. 
class FacePanel extends JPanel {
    private StraightFace myStraightFace;
    private SadFace mySadFace;
    private HappyFace myHappyFace;
    ArrayList<Face> faces = new ArrayList<Face>();

    public FacePanel() {
        Random rand = new Random();
        int randIntFaces = rand.nextInt((10 - 3) + 1) + 3;

        for(int i = 0; i < randIntFaces; i++) {
            int randFace = (int)(Math.random() * 3 + 1);
            int randX = rand.nextInt((750 - 50) + 1) + 50;
            int randY = rand.nextInt((500 - 50) + 1) + 50;
            int randWidth = rand.nextInt((300 - 100) + 1) + 100;
            int randHeight = rand.nextInt((300 - 100) + 1) + 100;
            if (randFace == 1) {
                myStraightFace = new StraightFace(randX, randY, randWidth, randHeight);
                faces.add(myStraightFace);
            }
            else if (randFace == 2) {
                mySadFace = new SadFace(randX, randY, randWidth, randHeight);
                faces.add(mySadFace);
            }
            else if (randFace == 3) {
                myHappyFace = new HappyFace(randX, randY, randWidth, randHeight);
                faces.add(myHappyFace);
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Face faces : faces) {
            faces.paintComponent(g);
          }
    }
}
//Creates the application frame, the FacePanel which created the faces is passed into this
class FaceFrame extends JFrame {
    public FaceFrame() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame myFrame = new JFrame("FaceDraw JCR");
        myFrame.setBounds(100,100,900,600);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FacePanel myFacePanel = new FacePanel();
        myFrame.add(myFacePanel);
        myFrame.setVisible(true);

    }
}
//Main function, only needed to call for the creation of the application frame since the facepanel is being fed into the faceframe
public class FaceDrawJCR {
    public static void main(String[] args) {
        System.out.println("FaceDraw JCR!!!");
        FaceFrame myFrame = new FaceFrame();

    }
}
