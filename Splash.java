import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;

public class Splash {

   Sprite bike;
   Sprite bus;
   int i;
   int i2;
   int kill;

   public Splash(Sprite bike, Sprite bus) {
      this.bike = bike;
      this.bus = bus;
      this.i = 0;
      this.i2 = 64;
      this.kill = 3;
   }
   
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