import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CharSelect implements MouseListener, MouseMotionListener {

   int xHover;
   int yHover;
   int xClick;
   int yClick;
   Sprite rebecca;
   Sprite benji;
   
   public CharSelect(Sprite rebecca, Sprite benji) {
      this.rebecca = rebecca;
      this.benji = benji;
      xHover = 0;
      yHover = 0;
      xClick = 0;
      yClick = 0;
   }
   
   public void mouseClicked (MouseEvent e) {
      
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
      
      
      g.setColor(Color.yellow);
      for (int i = 0; i < 800; i += 80) {
         g.fillRect(i,130,50,20);
         g.fillRect(i,360,50,20);
         g.drawImage(rebecca.getImage(), 460, 175, null);
         g.drawImage(benji.getImage(), 160,175, null);
      }
      g.setColor(Color.black);
      g.setFont(new Font("Helvetica Neue", Font.BOLD, 44));
      g.drawString("Character Select", 213, 80);
      
      g.setColor(Color.white);
      
      g.fillRect(125,350,230,75);
      g.fillRect(425,350,230,75);

      g.setColor(new Color(209, 196, 56));
      if (xHover>125 && xHover<355 && yHover>350 && yHover < 425) 
         g.fillRect(125,350,230,75);
      if (xHover>425 && xHover<655 && yHover>350 && yHover < 425) 
         g.fillRect(425,350,230,75);         
      
      g.setColor(Color.black);
      g.drawRect(125,350,230,75);
      g.drawRect(425,350,230,75);
      g.setFont(new Font("Helvetica Neue", Font.PLAIN, 24));
      g.drawString("Benji", 213, 395);
      g.drawString("Rebecca", 500, 395);      
   }
}