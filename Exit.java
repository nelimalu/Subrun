import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;

public class Exit {

   Sprite car;
   int i;

   public Exit(Sprite car) {
      this.car = car;
      this.i = 0;
   }
   
   public void paint (Graphics g) {
 
         g.setColor(new Color(64, 63, 63));
         g.fillRect(0,0,800,500);
         g.setColor(new Color(252, 229, 199));
         g.setFont(new Font("Arial", Font.BOLD+Font.ITALIC, 44));
         g.drawString("Thanks for playing Subrun!", 30, 60);
         g.setColor(new Color(217, 210, 233));
         g.drawString("To be continued...", 365, 420);
         if (i < 900) {
           g.drawImage(car.getImage(), -100+i, 0, null);
           g.drawImage(car.getImage(), i+150, 75, null);
           g.drawImage(car.getImage(), i, 200, null);
           i+=6;
         } else {
            System.exit(0);
         }
                     
   }
}