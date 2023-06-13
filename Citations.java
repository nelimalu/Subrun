
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

public class Citations implements MouseListener, MouseMotionListener {

   /** stores x value of the mouse in real-time as it moves for interactive button hovers **/
   int xHover;
   /** stores y value of the mouse in real-time as it moves for interactive button hovers **/
   int yHover;
   /** stores x value on mouse click for button interaction **/
   int xClick;
   /** stores y value on mouse click for button interaction **/
   int yClick;



   public Citations() {
      xHover = 0;
      yHover = 0;
      xClick = 0;
      yClick = 0;

   }
   
    public void mouseClicked (MouseEvent e) {
      if (xHover < 120 && yHover > 415)
         Controller.changeScreen(2);
      
   }

   public void mousePressed (MouseEvent e) {
   }

   public void mouseReleased (MouseEvent e) {
   }

   public void mouseEntered (MouseEvent e) {

   }
   public void mouseExited (MouseEvent e) {

   }

   /**
    * Stores all mouse movements
    * Tracks coordinates of mouse in real time
    *
    * @param e the event to be processed
    */
   public void mouseMoved (MouseEvent e) {
      xHover = e.getX();
      yHover = e.getY();
   }

   public void mouseDragged (MouseEvent e) {
   }

  
   public void paint(Graphics g) {
      // Draw information and rules
      g.setColor(Color.black);
      g.setFont(new Font("Helvetica Neue", Font.BOLD, 36));
      g.drawString("Citations", 30, 60);
      g.setFont(new Font("Arial", Font.PLAIN, 14));
      g.drawString("Not Just Bikes. (2022, June 6). Why We Won't Raise Our Kids in Suburbia. YouTube.",30, 130);
      g.drawString("https://www.youtube.com/watch?v=oHlpmxLTxpw",30, 155);

      g.drawString("Crook, A. (n.d.). Very superstitious: How fact-free parenting policies Rob our kids of independence. ", 30, 190);
      g.drawString("5 Kids 1 Condo. https://5kids1condo.com/very-superstitious-how-fact-free-parenting-policies-rob-our-kids-of", 30, 215);
      g.drawString("-independence/", 30, 240);
      g.drawString("Drivers are killing more pedestrians in Canada every year. Here's why: Michael's essay | CBC radio.", 30, 275);
      g.drawString("(2019, February 1). CBC. http s://www.cbc.ca/radio/sunday/the-sunday-edition-for-february-3-2019", 30, 300);
      g.drawString("-1.4997146/drivers-are-killing-more-pedestrians-in-canada-every-year-here-s-why-michael-s-essay-1.4998615",30, 325);
      
      g.setColor(Color.black);
      g.setFont(new Font("Arial", Font.BOLD, 28));
      g.drawString("<- Back", 15, 440);

      if (xHover < 120 && yHover > 415) {
         g.setColor(Color.gray);
         g.drawString("<- Back", 15, 440);
      }
  }
}
