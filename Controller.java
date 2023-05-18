import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Controller {
   public Drawing drawing;
   public static JFrame frame = new JFrame("[ SUBRUN ] -- Vaughan Collective");
   
   public static int screen = 0;
   private boolean initScreen = true;
   
   /* INITIALIZE SCREENS */
   Menu menu = new Menu();
   
   
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
      for (KeyListener a : frame.getListeners(KeyListener.class)) {
         frame.removeKeyListener(a);
      }
   }
   
   class Drawing extends JComponent {  
      public void paint (Graphics g) {
         
         switch (screen) {
            case 0:
               if (initScreen) {
                  removeListeners();
                  frame.addMouseListener(menu);
                  initScreen = false;
               }
               menu.paint();
               
         }
         
      }
   }
   
}