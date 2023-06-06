import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MovementTest implements KeyListener {
   Player player;
   Obstacle[] obstacles;
   Drawing drawing;
   MessageBox box;
   static boolean[] buttons = {false, false, false, false};  // up down left right

   public MovementTest() {
      player = new Player(800 / 2, 500 / 2, new Sprite[] {
         new Sprite("assets/rebecca0.png"),
         new Sprite("assets/rebecca1.png"),
         new Sprite("assets/rebecca0.png"),
         new Sprite("assets/rebecca2.png")
      },
      new Sprite[] {
         new Sprite("assets/rebecca3.png"),
         new Sprite("assets/rebecca4.png"),
         new Sprite("assets/rebecca3.png"),
         new Sprite("assets/rebecca5.png")
      });
      
      obstacles = new Obstacle[] {
         new Obstacle(50, 50, new Sprite("assets/treetest.png"))
      };

      box = new MessageBox("TESTING");
      
      JFrame frame = new JFrame("Testing");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(800, 500);
      
      drawing = new Drawing();
      frame.add(drawing);
      frame.addKeyListener(this);
      frame.setVisible(true);


      
      Timer timer = new Timer(40, new ActionListener() {
         public void actionPerformed(ActionEvent a) {
            drawing.repaint();
         }
      });
      timer.start();
   }
   
   public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_UP)
         buttons[0] = true;
      if (e.getKeyCode() == KeyEvent.VK_DOWN)
         buttons[1] = true;
      if (e.getKeyCode() == KeyEvent.VK_LEFT)
         buttons[2] = true;
      if (e.getKeyCode() == KeyEvent.VK_RIGHT)
         buttons[3] = true;
    }
   
   public void keyReleased(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_UP)
         buttons[0] = false;
      if (e.getKeyCode() == KeyEvent.VK_DOWN)
         buttons[1] = false;
      if (e.getKeyCode() == KeyEvent.VK_LEFT)
         buttons[2] = false;
      if (e.getKeyCode() == KeyEvent.VK_RIGHT)
         buttons[3] = false;
   }
   public void keyTyped(KeyEvent e) {
      System.out.println("KEY TYPED");
   }
   
   class Drawing extends JComponent {
      public void paint (Graphics g) {
         //player.draw(g, buttons, obstacles);
         box.draw(g);
         
         for (int i = 0; i < obstacles.length; i++) {
            obstacles[i].draw(g);
         }
      }
   }
   
   public static void main (String[] args) {
      new MovementTest();
   }
   
}
