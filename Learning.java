import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Learning implements KeyListener {
   Drawing drawing;
   Sprite character
   int screen = 0;
   
   public Learning(String name) {
      if (name.equals("Rebecca");
         character = new Sprite("assets/rebecca0.png");
      else
         
   
      JFrame frame = new JFrame("Testing");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(800, 500);
      
      drawing = new Drawing();
      frame.add(drawing);
      frame.addKeyListener(this);
      frame.setVisible(true);
      frame.dispose();
      
   }
   
   public void keyReleased(KeyEvent e) {
      screen++;
   }
   
   public void keyTyped(KeyEvent e) {}
   public void keyPressed(KeyEvent e) {}
   
   public void drawInfo() {
      
   }
   
   public void introScreen(Graphics g) {
      
   }
   
   class Drawing extends JComponent {
      public void paint (Graphics g) {
         switch (screen) {
            case 0:
               introScreen();
               break;
            case 1:
               //suburbsScreen();
               break;
            case 2:
               //travelScreen();
               break;
            case 3:
               //waysToTravelScreen();
               break;
            case 4:
               //bikeScreen();
               break;
            case 5:
               //walkScreen();
               break;
            case 6:
               //busScreen();
               break;
            case 7:
               //uberScreen();
               break;
            case 8:
               //endScreen();
               break;
            case 9:
               System.out.println("DONE -- send back to main menu");
               break;
         }
      }
   }
   
   public static void main (String[] args) {
      new Learning();
   }

   
}