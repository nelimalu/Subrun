import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

   public class Info implements MouseListener, MouseMotionListener {

   int xHover;
   int yHover;
   int xClick;
   int yClick;
   Sprite money;
   Sprite benji;
   
   public Info(Sprite money, Sprite benji) {
      this.money = money;
      this.benji = benji;
      xHover = 0;
      yHover = 0;
      xClick = 0;
      yClick = 0;;
   }
   
   public void mouseClicked (MouseEvent e) {
      if (xHover < 120 && yHover > 415) 
         Controller.changeScreen(0);
   }

   public void mousePressed (MouseEvent e) {
   }

   public void mouseReleased (MouseEvent e) {
   }

   public void mouseEntered (MouseEvent e) {
      
   }
   public void mouseExited (MouseEvent e) {
   
   }
   
   public void mouseMoved (MouseEvent e) {
      xHover = e.getX();
      yHover = e.getY();

   }
   
   public void mouseDragged (MouseEvent e) {
   }
 
   public void paint (Graphics g) {
      
      g.setColor(new Color(64, 63, 63));
      g.fillRect(0,0,800,500);
      
      
      g.setColor(Color.white);
      g.setFont(new Font("Helvetica Neue", Font.BOLD, 44));
      g.drawString("Information & Rules", 30, 60);
      g.setColor(new Color(225, 225, 225));
      g.setFont(new Font("Arial", Font.PLAIN, 22));
      g.drawString("-- Created by Vaughan Collective (Luka, Brian) --", 290, 90);
      
      g.setColor(new Color(252, 229, 199));
      g.setFont(new Font("Arial", Font.BOLD+Font.ITALIC, 22));
      g.drawString("Subrun", 30, 130);
      g.setFont(new Font("Arial", Font.PLAIN, 22));
      g.drawString("puts you in a suburban neighborhood, where", 113, 130);
      g.drawString("you get to experience first-hand the struggles of living", 30, 162);
      g.drawString("in these areas! Survive a day with limited funds and ", 30, 194);
      g.drawString("accomplish your tasks in the shortest amount of time", 30, 226);
      g.drawString("possible.", 30, 258);
      g.setColor(new Color(217, 210, 233));
      g.setFont(new Font("Arial", Font.BOLD, 22));
      g.drawString("Rules: ", 310, 270);
      g.setFont(new Font("Arial", Font.PLAIN, 22));
      g.drawString(" - You have 1 life, so tread carefully!", 310, 302);
      g.drawString(" - Your score is assigned based on how quick", 310, 334);
      g.drawString("   you complete your tasks.", 310, 366);
      g.drawString(" - Your cannot use modes of transport which", 310, 398);
      g.drawString("   costs exceed your wallet balance.", 310, 430);
      g.setFont(new Font("Arial", Font.BOLD, 30));
      g.setColor(Color.white);
      g.drawString("<- Back", 15, 440);
      if (xHover < 120 && yHover > 415) {
         g.setFont(new Font("Arial", Font.BOLD, 30));
         g.setColor(Color.yellow);
         g.drawString("<- Back", 15, 440);
         
         
      }
      g.drawImage(benji.getImage(), 570, 110, null);
      g.drawImage(money.getImage(), 80, 260, null);
   }
   
}
