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
      if (xHover < 120 && yHover > 415)
         Controller.changeScreen(0);
      if ((xHover>125 && xHover<355 && yHover>350 && yHover < 425) || (xHover>425 && xHover<655 && yHover>350 && yHover < 425)) {
         Controller.changeScreen(6);
         Controller.maze = new Maze();
         Controller.escapeRoom = new EscapeRoom();
         Controller.learning = new Learning(new Sprite("assets/laptop.png", 1),new Sprite("assets/redHouse.png", 7),new Sprite("assets/blueHouse.png", 7),new Sprite("assets/orangeHouse.png", 7), new Sprite("assets/chalkboard.png", 4), new Sprite("assets/suburbs.png", 1), new Sprite("assets/bike.png", 6), new Sprite("assets/car.png", 6),new Sprite("assets/bus.png", 6), new Sprite ("assets/thinking.png",1), new Sprite("assets/sign.png",1), new Sprite("assets/benji0.png",5), new Sprite("assets/benji0.png", 8));
      }
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

      }
      g.setColor(Color.black);
      g.setFont(new Font("Helvetica Neue", Font.BOLD, 44));
      g.drawString("Character Select", 213, 80);

      g.setColor(Color.white);

      g.fillRect(125,330,230,75);
      g.fillRect(425,330,230,75);

      g.setColor(new Color(209, 196, 56));
      if (xHover>125 && xHover<355 && yHover>350 && yHover < 425) {
         g.fillRect(125, 330, 230, 75);
         Controller.CHARACTER = "Benji";

      }
      if (xHover>425 && xHover<655 && yHover>350 && yHover < 425) {
         g.fillRect(425, 330, 230, 75);
         Controller.CHARACTER = "Rebecca";
      }

      g.setColor(Color.black);
      g.drawRect(125,330,230,75);
      g.drawRect(425,330,230,75);
      g.setFont(new Font("Helvetica Neue", Font.PLAIN, 24));
      g.drawString("Benji", 213, 375);
      g.drawString("Rebecca", 500, 375);
      g.setFont(new Font("Arial", Font.BOLD, 30));
      g.drawImage(rebecca.getImage(), 460, 155, null);
      g.drawImage(benji.getImage(), 160,155, null);

      g.setColor(Color.white);
      g.drawString("<- Back", 15, 440);
      if (xHover < 120 && yHover > 415) {
         g.setFont(new Font("Arial", Font.BOLD, 30));
         g.setColor(Color.yellow);
         g.drawString("<- Back", 15, 440);


      }

   }
}