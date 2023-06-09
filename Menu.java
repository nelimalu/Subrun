import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * BusGame class handles movement between the character select screen, which continues to the game, the info screen, and exiting
 *
 * <strong>Course info:</strong>
 * ICS4U0 with V. Krasteva
 *
 * @version 1.0
 * @author Luka Jovanovic & Brian Song
 * Created on 2023/05/16
 */
public class Menu implements MouseListener, MouseMotionListener {
   
   /** X position of the mouse */
   int xHover;
   
   /** Y position of the mouse */
   int yHover;
   
   /**
    * Menu constructor that initializes the mouse position
    */
   public Menu() {
      xHover = 0;
      yHover = 0;
   }
   
   /**
    * Handles mouse clicks by checking where the mouse is on the click, and if it's on a button, it sends the user to the respective screen
    * @param e The mouse event
    */
   public void mouseClicked(MouseEvent e) {
      if (xHover>270 && xHover<480 && yHover>175 && yHover < 235) {
         System.out.println("PLAY");
         Controller.changeScreen(1);
      } if (xHover>270 && xHover<480 && yHover>275 && yHover < 335) {
         System.out.println("INFO");
         Controller.changeScreen(2);
      } if (xHover>270 && xHover<480 && yHover>375 && yHover < 435) {
         System.out.println("EXIT");
         Controller.changeScreen(4);
      }
   }

   public void mousePressed (MouseEvent e) {}
   public void mouseReleased (MouseEvent e) {}
   public void mouseEntered (MouseEvent e) {}
   public void mouseExited (MouseEvent e) {}
   public void mouseDragged (MouseEvent e) {}
   
   /**
    * Keeps track of the mouse position using xHover and yHover
    * @param e The mouse event
    */
   public void mouseMoved (MouseEvent e) {
      xHover = e.getX();
      yHover = e.getY();
   }
   
   /**
    * Draws all the text, title, background and buttons for the main menu
    * @param g The Graphics object for drawing
    */
   public void paint (Graphics g) {

      g.setColor(new Color(64, 63, 63));
      g.fillRect(0,0,800,500);


      g.setColor(Color.yellow);
      for (int i = 0; i < 800; i += 80) {
         g.fillRect(i,130,50,20);
         g.fillRect(i,360,50,20);
      }
      g.setColor(Color.black);
      g.setFont(new Font("Helvetica Neue", Font.BOLD, 54));
      g.drawString("S U B R U N", 225, 100);

      g.setColor(Color.white);

      g.fillRect(270,150,210,60);
      g.fillRect(270,250,210,60);
      g.fillRect(270,350,210,60);
      g.setColor(Color.red);
      if (xHover>270 && xHover<480 && yHover>175 && yHover < 235)
         g.fillRect(270,150,210,60);
      if (xHover>270 && xHover<480 && yHover>275 && yHover < 335)
         g.fillRect(270,250,210,60);
      if (xHover>270 && xHover<480 && yHover>375 && yHover < 435)
         g.fillRect(270,350,210,60);

      g.setColor(Color.black);
      g.drawRect(270,150,210,60);
      g.drawRect(270,250,210,60);
      g.drawRect(270,350,210,60);
      g.setFont(new Font("Helvetica Neue", Font.PLAIN, 24));
      g.drawString("Play", 350, 190);
      g.drawString("Information", 315, 290);
      g.drawString("Exit", 353, 390);

   }
}
