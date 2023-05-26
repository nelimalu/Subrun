import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Controller {
   public Drawing drawing;
   public static JFrame frame = new JFrame("[ SUBRUN ] -- Vaughan Collective");
   
   public static int screen = 3;
   public static boolean initScreen = true;
   public static Timer timer;
   
   /* INITIALIZE SCREENS */
   Menu menu = new Menu();
   CharSelect charSelect = new CharSelect(new Sprite("assets/rebecca0.png", 10), new Sprite("assets/benji.png", 10));
   Info info = new Info(new Sprite("assets/money.png", 5), new Sprite("assets/benji.png", 10));
   Maze maze = new Maze();
   
   public Controller() {
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(800, 500);
      
      drawing = new Drawing();
      frame.add(drawing);
      //frame.addKeyListener(this);
      frame.setVisible(true);

      timer = new Timer(40, new ActionListener() {
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
            case 0:  // menu
               if (initScreen) {
                  removeListeners();
                  frame.addMouseListener(menu);
                  frame.addMouseMotionListener(menu);
                  initScreen = false;
               }
               menu.paint(g);
               break;
            case 1:  // character select
               if (initScreen) {
                  removeListeners();
                  frame.addMouseListener(charSelect);
                  frame.addMouseMotionListener(charSelect);
                  initScreen = false;
               }
               charSelect.paint(g);
               break;
            case 2:  // info screen
               if (initScreen) {
                  removeListeners();
                  frame.addMouseListener(info);
                  frame.addMouseMotionListener(info);
                  initScreen = false;
               }
               info.paint(g);
               break;
            case 3: // Maze
               if (initScreen) {
                  removeListeners();
                  frame.addKeyListener(maze);
                  initScreen = false;
               }
               maze.paint(g);
               break;
               
         }
         
      }
   }
   
}