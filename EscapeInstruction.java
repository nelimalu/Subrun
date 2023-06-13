
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The Info class handles mouse events and displays information and rules for the game.
 *
 * <strong>Course info:</strong>
 * ICS4U0 with V. Krasteva
 *
 * @version 1.0
 * @author [20%] Luka Jovanovic & [80%] Brian Song
 * Created on 2023/05/29
 * Luka: drawing sprites
 * Brian: text, painting frame
 */

public class EscapeInstruction implements MouseListener, MouseMotionListener {

   /** stores x value of the mouse in real-time as it moves for interactive button hovers **/
   int xHover;
   /** stores y value of the mouse in real-time as it moves for interactive button hovers **/
   int yHover;
   /** stores x value on mouse click for button interaction **/
   int xClick;
   /** stores y value on mouse click for button interaction **/
   int yClick;
   /** Sprite for money **/
   Sprite rebecca;
   /** Sprite for benji **/
   Sprite pin;
   
   /**
    * Info constructor
    *
    * imports sprites for money and benji
    * assigns base values to hover and click variables
    *
    * @param money Sprite for money
    * @param benji Sprite for benji
    */
   public EscapeInstruction(Sprite rebecca, Sprite pin) {
      this.rebecca = rebecca;
      this.pin = pin;
      xHover = 0;
      yHover = 0;
      xClick = 0;
      yClick = 0;
   }

   /**
    * Handles the mouseClicked event.
    * Returns to main menu upon back button being pressed
    *
    * @param e the MouseEvent object
    */
   public void mouseClicked(MouseEvent e) {
         if (xHover < 535 && xHover> 260 && yHover>410 && yHover< 460)
         Controller.changeScreen(8);
   }

   public void mousePressed(MouseEvent e) {
   }

   public void mouseReleased(MouseEvent e) {
   }

   public void mouseEntered(MouseEvent e) {

   }

   public void mouseExited(MouseEvent e) {

   }

   /**
    * Stores all mouse movements
    * Tracks coordinates of mouse in real time
    *
    * @param e the event to be processed
    */
   public void mouseMoved(MouseEvent e) {
      xHover = e.getX();
      yHover = e.getY();
   }

   public void mouseDragged(MouseEvent e) {
   }

   /**
    * Paint method
    * Provides brief premise and information as to the operation of the game
    * Presents user with a sprite of benji and money
    *
    * @param g the Graphics object to be painted
    */
   public void paint(Graphics g) {
      // Draw information and rules
      g.setColor(Color.black);
      g.setFont(new Font("Helvetica Neue", Font.BOLD, 44));
      g.drawString("Welcome to the Escape", 30, 60);
      g.drawString("Room Level!", 30, 110);
      g.setFont(new Font("Arial", Font.PLAIN, 20));
      g.drawString("Congrats on reaching the last level of ", 30, 170);
      g.setFont(new Font("Arial", Font.BOLD+Font.ITALIC, 20));
      g.drawString("Subrun!", 360, 170);
      g.setFont(new Font("Arial", Font.PLAIN, 20));
      g.drawString("Here, you'll tackle the problems presented by urban sprawl", 30, 210);
      g.drawString("to the youth heads-on, using the information you learned in the", 30, 235);
      g.drawString("learning level, and the types of transportation you found in the", 30, 260);
      g.drawString("maze level.", 30, 285);
      
      g.drawString("Talk to the NPCs in the level to play each minigame, and reach", 30, 325);
      g.drawString("the minimum score to complete it. Beat all 3 to complete the game!", 30, 350);
      g.drawRect(260, 380, 275, 50);
      
      if (xHover < 535 && xHover> 260 && yHover>410 && yHover< 460) {
         g.setColor(new Color(255,253,208));
         g.fillRect(260,380,275,50);
      }
      
      g.setColor(Color.black);
      g.setFont(new Font("Arial", Font.BOLD, 24));
      g.drawString("Continue", 340, 415);

   }
}

