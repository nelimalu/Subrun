import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Controller {
   public Drawing drawing;
   public static JFrame frame = new JFrame("[ SUBRUN ] -- Vaughan Collective");
   
   public static int screen = 0;
   public static boolean initScreen = true;
   
   /* INITIALIZE SCREENS */
   Menu menu = new Menu();
   CharSelect charSelect = new CharSelect(new Sprite("assets/rebecca0.png", 10), new Sprite("assets/benji.png", 10));
   
   
   public Controller() {
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(800, 500);
      
      drawing = new Drawing();
      frame.add(drawing);
      //frame.addKeyListener(this);
      frame.setVisible(true);

      Timer timer = new Timer(40, new ActionListener() {
         public void actionPerformed(ActionEvent a) {
            drawing.repaint();
         }
      });
      timer.start();
   }
   
   private void removeListeners() {
      for (KeyListener a : frame.getListeners(KeyListener.class))
         frame.removeKeyListener(a);
      for (MouseListener b : frame.getListeners(MouseListener.class))
         frame.removeMouseListener(b);
      for (MouseMotionListener c : frame.getListeners(MouseMotionListener.class))
         frame.removeMouseMotionListener(c);
   }
   
   public static void changeScreen(int newScreen) {
      screen = newScreen;
      initScreen = true;
   }
   
   class Drawing extends JComponent {  
      public void paint (Graphics g) {
         
         switch (screen) {
            case 0:
               if (initScreen) {
                  removeListeners();
                  frame.addMouseListener(menu);
                  frame.addMouseMotionListener(menu);
                  initScreen = false;
               }
               menu.paint(g);
               break;
            case 1:
               if (initScreen) {
                  removeListeners();
                  frame.addMouseListener(charSelect);
                  frame.addMouseMotionListener(charSelect);
                  initScreen = false;
               }
               charSelect.paint(g);
               break;
               
         }
         
      }
   }
   
}