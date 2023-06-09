import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
/**
 * Splash class has animations coming in from both sides of the screen
 * panning into a fading-in Subrun logo at the middle of the screen to start the game.
 *
 * <strong>Course info:</strong>
 * ICS4U0 with V. Krasteva
 *
 * @version 1.0
 * @author [0%] Luka Jovanovic & [100%] Brian Song
 * Created on 2023/06/02
 * Brian: entry animation, fadeout, paint
 */
public class Splash {
   /** bike sprite to be animated */
   Sprite bike;
   /** bus sprite to be animated */
   Sprite bus;
   /** first timer tracker, used to move bike and bus in */
   int i;
   /** second timer tracker, used to fade black rectangle logo in */
   int i2;
   /** final timer tracker, holds the Subrun logo for set time before moving into menu */
   int kill;

   /**
    * Splash constructor, assigns default values to timers and sets sprites
    *
    * @param bike Sprite for bike animated
    * @param bus Sprite for bus animated
    */
   public Splash(Sprite bike, Sprite bus) {
      this.bike = bike;
      this.bus = bus;
      this.i = 0;
      this.i2 = 64;
      this.kill = 3;
   }
   /**
    * Paint method
    * Loads 3 animations in succession: the bike and the bus coming in, the logo fading in, and the
    * text coming in with the vehicles stopping
    *
    * @param g the Graphics object to be painted
    */
   public void paint (Graphics g) {
 
         g.setColor(new Color(64, 63, 63));
         g.fillRect(0,0,800,500);
         if (i <= 300) {
            g.setColor(Color.yellow);
            for (int i = 0; i < 800; i += 80) {
               g.fillRect(i,240,50,20);
            }
            g.drawImage(bus.getImage(), -100+i, -50, null);
            g.drawImage(bike.getImage(), 685-i, 250, null);
            i+= 5;
         } else if (i <= 375) {
            g.setColor(Color.yellow);
            for (int i = 0; i < 800; i += 80) {
               g.fillRect(i,240,50,20);
            }
            g.drawImage(bus.getImage(), -100+i, -50, null);
            g.drawImage(bike.getImage(), 685-i, 250, null);
            i+= 5;
            g.setColor(new Color(i2, i2, i2));
            g.fillRect(220, 180, 340, 130);
            i2 -= 2;
         } else if (i2 >= 40) {
            g.setColor(Color.yellow);
            for (int i = 0; i < 800; i += 80) {
               g.fillRect(i,240,50,20);
            }
            g.drawImage(bus.getImage(), 275, -50, null);
            g.drawImage(bike.getImage(), 310, 250, null);
            g.setColor(new Color(i2, i2, i2));
            g.fillRect(220, 180, 340, 130);
            i2 -= 2;
         } else if (kill < 75) {
            g.setColor(Color.yellow);
            for (int i = 0; i < 800; i += 80) {
               g.fillRect(i,240,50,20);
            }
            g.drawImage(bus.getImage(), 275, -50, null);
            g.drawImage(bike.getImage(), 310, 250, null);
            g.setColor(new Color(30,30,30));
            g.fillRect(220, 180, 340, 130);
            g.setFont(new Font("Helvetica Neue", Font.BOLD, 54));
            g.setColor(Color.WHITE);
            g.drawString("S U B R U N", 238, 263);
            kill++;
         } else {
             Controller.changeScreen(0);
         }
            
   }
}