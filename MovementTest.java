import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class MovementTest implements KeyListener {
   Player player;
   Drawing drawing;

   public MovementTest() {
      player = new Player(800 / 2, 500 / 2, new Sprite[] {
         new Sprite("assets/rebecca0.png"),
         new Sprite("assets/rebecca1.png"),
         new Sprite("assets/rebecca0.png"),
         new Sprite("assets/rebecca2.png"),
      },
      new Sprite[] {
      
      });
      
      JFrame frame = new JFrame("Testing");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(800, 500);
      
      drawing = new Drawing();
      frame.add(drawing);
      frame.addKeyListener(this);
      frame.setVisible(true);
   }
   
   public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_UP)
         player.move("UP");
      if (e.getKeyCode() == KeyEvent.VK_DOWN)
         player.move("DOWN");
      if (e.getKeyCode() == KeyEvent.VK_LEFT)
         player.move("LEFT");
      if (e.getKeyCode() == KeyEvent.VK_RIGHT)
         player.move("RIGHT");
      drawing.repaint();
    }
   
   public void keyReleased(KeyEvent e) {}
   public void keyTyped(KeyEvent e) {}
   
   class Drawing extends JComponent {
      public void paint (Graphics g) {
         
         player.draw(g);
      }
   }
   
   public static void main (String[] args) {
      new MovementTest();
   }
   
}
