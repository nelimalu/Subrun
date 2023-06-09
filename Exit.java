import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
/**
 * CharSelect class plays an animated exit to the game, where
 * cars enter from the left of the frame and the game concludes as the cars disappear.
 *
 * <strong>Course info:</strong>
 * ICS4U0 with V. Krasteva
 *
 * @version 1.0
 * @author [0%] Luka Jovanovic & [100%] Brian Song
 * Created on 2023/06/03
 */
public class Exit {

    /** car sprite to be animated */
   Sprite car;
   /** timer tracker to time certain actions like fading, movement */
   int i;

    /**
     * Exit constructor
     * Assigns default/input values to instance variables
     *
     * @param car car sprite
     */
   public Exit(Sprite car) {
      this.car = car;
      this.i = 0;
   }

    /**
     * Paint method
     * Outro screen, cars will enter from the left and progress at
     * a steady pace until they all go off the screen on the right side,
     * at which point the program will force close.
     *
     * @param g Graphics object to be painted
     */
   public void paint (Graphics g) {
 
         g.setColor(new Color(64, 63, 63));
         g.fillRect(0,0,800,500);
         g.setColor(new Color(252, 229, 199));
         g.setFont(new Font("Arial", Font.BOLD+Font.ITALIC, 44));
         g.drawString("Thanks for playing Subrun!", 30, 60);
         g.setColor(new Color(217, 210, 233));
         g.drawString("To be continued...", 365, 420);
         if (i < 900) {
           g.drawImage(car.getImage(), -100+i, 85, null);
           g.drawImage(car.getImage(), i+150, 150, null);
           g.drawImage(car.getImage(), i, 275, null);
           i+=6;
         } else {
            System.exit(0);
         }
                     
   }
}