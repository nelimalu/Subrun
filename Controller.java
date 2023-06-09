import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Controller class manages navigation between different classes and stores necessary
 * global variables such as level unlock states and character choice.
 *
 * <strong>Course info:</strong>
 * ICS4U0 with V. Krasteva
 *
 * @version 1.0
 * @author [50%] Luka Jovanovic & [50%] Brian Song
 * Created on 2023/05/29
 */
public class Controller {
   /** drawing object to store paints */
   public Drawing drawing;
   /** creating frame & assigning header name */
   public static JFrame frame = new JFrame("[ SUBRUN ] -- Vaughan Collective");
   /** current screen index corresponding with a switch statement to switch paintings */
   public static int screen = 5;  // default 5
   /** limits the addition of listeners to frame to once */
   public static boolean initScreen = true;
   /** timer to manage framerate and track scores throughought the progression of the game */
   public static Timer timer;
   /** hitbox toggle for collision testing */
   public static final boolean SHOW_HITBOXES = false;
   /** maze unlock state */
   public static boolean mazeUnlocked = false;
   /** escape room unlock state */
   public static boolean escapeUnlocked = false;
   /** chosen character for progression of game */
   public static String CHARACTER = "Rebecca";

   /* INITIALIZE SCREENS */
   /** Menu screen with scales for sprite images*/
   Menu menu = new Menu();
   /** Character select screen */
   CharSelect charSelect = new CharSelect(new Sprite("assets/rebecca0.png", 10), new Sprite("assets/benji0.png", 10));
   /** Info screen with scales for sprite images*/
   Info info = new Info(new Sprite("assets/money.png", 5), new Sprite("assets/benji0.png", 10));
   /** Splash screen with scales for sprite images*/
   Splash splash = new Splash(new Sprite("assets/bike.png", 5), new Sprite("assets/bus.png", 7));
   /** Exit screen with scales for sprite images*/
   Exit exit = new Exit(new Sprite("assets/car.png", 5));
   /** Maze screen with scales for sprite images*/
   public static Maze maze = new Maze();

   /** Escape room screen */
   public static EscapeRoom escapeRoom = new EscapeRoom();
   /** Level select screen */
   LevelSelect levelSelect = new LevelSelect();
   /** Learning screen with scales for sprite images */
   public static Learning learning = new Learning(new Sprite("assets/laptop.png", 1),new Sprite("assets/redHouse.png", 7),new Sprite("assets/blueHouse.png", 7),new Sprite("assets/orangeHouse.png", 7), new Sprite("assets/chalkboard.png", 4), new Sprite("assets/suburbs.png", 1), new Sprite("assets/bike.png", 6), new Sprite("assets/car.png", 6),new Sprite("assets/bus.png", 6), new Sprite("assets/thinking.png", 1), new Sprite("assets/sign.png", 1), new Sprite("assets/benji0.png",5), new Sprite("assets/benji0.png", 8));

   /**
    * Constructor for controller class
    * Sets dimensions for JFrame and set close operation
    * Starts a timer with 40 as delay value
    */
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

   /**
    * removeListener method
    * removes all the listeners from the previous event that happened to make sure
    * only current relevant listener still runs
    */
   private void removeListeners() {
      for (KeyListener a : frame.getListeners(KeyListener.class))
         frame.removeKeyListener(a);
      for (MouseListener b : frame.getListeners(MouseListener.class))
         frame.removeMouseListener(b);
      for (MouseMotionListener c : frame.getListeners(MouseMotionListener.class))
         frame.removeMouseMotionListener(c);
   }

   /**
    * method to switch screen
    * sets screen variable to the new one inputted in the switch statement
    * @param newScreen screen index
    */
   public static void changeScreen(int newScreen) {
      screen = newScreen;
      initScreen = true;
   }
   /**
    * Drawing class passes through various classes
    * to navigate to the next screen
    */
   class Drawing extends JComponent {
      /**
       * Paint method
       * Removes pre-existing listeners, adds new one, and switches to new screen
       *
       * @param g  the <code>Graphics</code> context in which to paint
       */
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
                  frame.addMouseListener(learning);
                  initScreen = false;
               }
               learning.drawing.paint(g);
               break;
            case 8:  // escape room
               if (initScreen) {
                  removeListeners();
                  frame.addMouseListener(escapeRoom);
                  frame.addKeyListener(escapeRoom);
                  frame.addMouseMotionListener(escapeRoom);
                  initScreen = false;
               }
               escapeRoom.paint(g);
               break;



         }


      }
   }

}