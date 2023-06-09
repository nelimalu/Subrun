import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Controller {
   public Drawing drawing;
   public static JFrame frame = new JFrame("[ SUBRUN ] -- Vaughan Collective");
   public static int screen = 0;  // default 5
   public static boolean initScreen = true;
   public static Timer timer;
   public static final boolean SHOW_HITBOXES = false;
   public static boolean mazeUnlocked = false;
   public static boolean escapeUnlocked = false;

   public static String CHARACTER = "Rebecca";

   /* INITIALIZE SCREENS */
   Menu menu = new Menu();
   CharSelect charSelect = new CharSelect(new Sprite("assets/rebecca0.png", 10), new Sprite("assets/benji0.png", 10));
   Info info = new Info(new Sprite("assets/money.png", 5), new Sprite("assets/benji0.png", 10));
   Splash splash = new Splash(new Sprite("assets/bike.png", 5), new Sprite("assets/bus.png", 7));
   Exit exit = new Exit(new Sprite("assets/car.png", 5));
   public static Maze maze = new Maze();
   public static EscapeRoom escapeRoom = new EscapeRoom();
   LevelSelect levelSelect = new LevelSelect();
   public static Learning learning = new Learning(new Sprite("assets/laptop.png", 1),new Sprite("assets/redHouse.png", 7),new Sprite("assets/blueHouse.png", 7),new Sprite("assets/orangeHouse.png", 7), new Sprite("assets/chalkboard.png", 4), new Sprite("assets/suburbs.png", 1), new Sprite("assets/bike.png", 6), new Sprite("assets/car.png", 6),new Sprite("assets/bus.png", 6), new Sprite("assets/benji0.png", 8));


   public Controller() {
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(800, 500);
      frame.setResizable(false);

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

            case 5:
               if (initScreen) {
                  removeListeners();
                  initScreen = false;
               }
               splash.paint(g);
               break;
            case 4:
               if (initScreen) {
                  removeListeners();
                  initScreen = false;
               }
               exit.paint(g);
               break;

            case 3: // Maze
               if (initScreen) {
                  removeListeners();
                  frame.addMouseListener(maze);
                  frame.addKeyListener(maze);
                  frame.addMouseMotionListener(maze);
                  initScreen = false;
               }
               maze.paint(g);

               break;

            case 6:
               if (initScreen) {
                  removeListeners();
                  frame.addMouseListener(levelSelect);
                  frame.addMouseMotionListener(levelSelect);
               }
               levelSelect.paint(g);
               break;
            case 7:
               if (initScreen) {
                  removeListeners();
                  frame.addKeyListener(learning);
                  initScreen = false;
               }
               learning.drawing.paint(g);
               break;
            case 8:  // escape room
               if (initScreen) {
                  removeListeners();
                  frame.addMouseListener(escapeRoom);
                  frame.addKeyListener(escapeRoom);
                  initScreen = false;
               }
               escapeRoom.paint(g);
               break;



         }


      }
   }

}