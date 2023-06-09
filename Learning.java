import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Learning class manages 11 slides that explain the premise of the game. Upon reaching
 * the final slide, it navigates back to level select and unlocks the maze level.
 *
 * <strong>Course info:</strong>
 * ICS4U0 with V. Krasteva
 *
 * @version 1.0
 * @author [30%] Luka Jovanovic & [70%] Brian Song
 * Created on 2023/06/07
 */

public class Learning implements MouseListener {
   /** drawing object to rotate between frames */
   Drawing drawing;
   /** chosen character (either benji or rebecca) to progress throughout the level */
   Sprite character;
   /** index of current screen, increases on click and resets upon reaching final slide */
   int screen = 0;
   /** sprite for laptop on slide 1 and 11 */
   Sprite laptop;
   /** sprite for red house on slide 2 */
   Sprite redHouse;
   /** sprite for blue house on slide 2 */
   Sprite blueHouse;
   /** sprite for orange house on slide 2 */
   Sprite orangeHouse;
   /** sprite for chalkboard on slide 4-8 */
   Sprite chalkboard;
   /** image for suburbs on slide 3 */
   Sprite suburbs;
   /** sprite for bike on slide 4 and 5 */
   Sprite bike;
   /** sprite for car on slide 4 and 6 */
   Sprite car;
   /** sprite for bus on slide 4 and 7 */
   Sprite bus;
   /** sprite for walk on slide 4 and 8 */
   Sprite walk;
   /** sprite for think bubble slide 9 */
   Sprite thinking;
   /** sprite for sign on slide 10 */
   Sprite sign;
   /** sprite for petitioners on slide 10 */
   Sprite petitioner;

   /** array of MessageBoxes, dialogues stored by order of index appearance */
   private static MessageBox[] messages = {
           new MessageBox("Hey! Nice to meet you! Welcome to the town of Vaughan. Come on! I'll show you around!"),
           new MessageBox("Vaughan's a suburban neighborhood, and it's a pretty nice place to be: decently quiet, and great people. But there's a problem..."),
           new MessageBox("It’s really hard to get around here as a kid. If you look atthe map, you can see that all the houses are placed in strips, with wide sidewalk-less roads linking isolated communities, making transportation really annoying without a car."),
           new MessageBox("So really, if your parents can’t drive you, that gives you 4ways to get around as a kid here. You can walk, you can ridea bike, you can bus, or if you’re feeling rich, you can takean Uber."),
           new MessageBox("If you aren’t going too far, taking a bike’s a great option!There’s a lot of room to bike around, and it’s a nice littleworkout. Just remember biking on the sidewalk is illegal, sostick to the bike lanes!"),
           new MessageBox("Walking is also great, especially when the weather’s good. Everything’s far apart so walking can only take you so far, but it's there. Naturally, travel times can get quite long, and sidewalks can be designed pretty inconsistently too."),
           new MessageBox("Another option is the bus! Although the bus route system in our neighborhood is not great at all because of low funding, when the bus does end up coming, it can be a fast and eco-friendly way to get places. "),
           new MessageBox("And finally, if you feel like splurging a little, you could call an uber and have it take you where you need to go. Justmake sure you have your parent’s permission, and be careful how much you spend; Ubers are expensive!"),
           new MessageBox("Here's the thing: although these are all good ways to get around in a suburban neighborhood, it doesn't solve the overlying issue of urban sprawl in itself." ),
           new MessageBox("Consider petitioning for better public transit, walking, andbiking infrastructure in your neighborhood! Lawmakers will notice eventually if a lot of people petition for the same thing."),
           new MessageBox("And that’s just about all you need to know to get around in our neighborhood. Now, you try walking a day in my shoes! Just remember everything I told you!")
   };

   /**
    * Learning constructor:
    * creates a player object based on character selected previously
    * creates a drawing that rotates through paint methods
    * imports sprites to be used in the paint methods
    * @param laptop Laptop sprite
    * @param redHouse Red house sprite
    * @param blueHouse Blue house sprite
    * @param orangeHouse Orange house sprite
    * @param chalkboard Chalkboard sprite
    * @param suburbs Suburbs image
    * @param bike Bike sprite
    * @param car Car sprite
    * @param bus Bus sprite
    * @param thinking Thinking Sprite
    * @param sign Sign sprite
    * @param petitioner Petitioner sprite (benji)
    * @param walk Walk sprite
    */
   public Learning(Sprite laptop, Sprite redHouse, Sprite blueHouse, Sprite orangeHouse, Sprite chalkboard, Sprite suburbs, Sprite bike, Sprite car, Sprite bus, Sprite thinking, Sprite sign, Sprite petitioner, Sprite walk) {
      if (Controller.CHARACTER.equals("Rebecca")) {
         character = new Sprite("assets/rebecca0.png", 12);
      } else if (Controller.CHARACTER.equals("Benji")) {
         character = new Sprite("assets/benji0.png", 12);
      }
      JFrame frame = new JFrame("Testing");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(800, 500);

      this.laptop = laptop;
      this.redHouse = redHouse;
      this.blueHouse = blueHouse;
      this.orangeHouse = orangeHouse;
      this.chalkboard = chalkboard;
      this.suburbs = suburbs;
      this.bike = bike;
      this.car = car;
      this.bus = bus;
      this.walk = walk;
      this.thinking = thinking;
      this.sign = sign;
      this.petitioner = petitioner;

      drawing = new Drawing();
      frame.add(drawing);
      frame.addMouseListener(this);
      frame.setVisible(true);
      frame.dispose();

   }

   /**
    * Handle all mouse clicks
    * When mouse is clicked, increase screen index by one
    * @param e the event to be processed
    */
   public void mouseClicked (MouseEvent e) {
      screen++;
   }

   public void mousePressed (MouseEvent e) {
   }

   public void mouseReleased (MouseEvent e) {
   }

   public void mouseEntered (MouseEvent e) {

   }
   public void mouseExited (MouseEvent e) {

   }

   /**
    * Intro screen slide
    * First introduction to suburban neighborhood
    * @param g Graphics object for drawing
    */
   public void introScreen(Graphics g) {
      g.drawImage(laptop.getImage(), 200, -50, null);
      g.drawImage(character.getImage(), 525, 140, null);
      messages[0].draw(g);
   }
   /**
    * Suburbs screen slide
    * User presented with the existence of a problem
    * @param g Graphics object for drawing
    */
   public void suburbsScreen(Graphics g) {
      g.drawImage(redHouse.getImage(), 200, 100, null);
      g.drawImage(blueHouse.getImage(), 400, 70, null);
      g.drawImage(orangeHouse.getImage(), 300, 120, null);
      g.drawImage(character.getImage(), 525, 140, null);
      messages[1].draw(g);
   }
   /**
    * Travel screen slide
    * Birdseye view of suburbs shows urban sprawl
    * @param g Graphics object for drawing
    */
   public void travelScreen(Graphics g) {
      g.drawImage(suburbs.getImage(),0,0,null);
      g.drawImage(character.getImage(), 525, 140, null);
      messages[2].draw(g);
   }
   /**
    * Ways to travel screen slide
    * Introduces the 4 ways to travel in game: bike, car, walk, bus
    * @param g Graphics object for drawing
    */
   public void waysToTravelScreen(Graphics g) {
      g.drawImage(chalkboard.getImage(), 100, -50, null);
      g.drawImage(walk.getImage(), 170, 150, null);
      g.drawImage(car.getImage(), 200, 80, null);
      g.drawImage(bus.getImage(), 280, 110, null);
      g.drawImage(bike.getImage(), 330, 40, null);
      g.drawImage(character.getImage(), 525, 140, null);
      messages[3].draw(g);
   }
   /**
    * Bike screen slide
    * Pros, cons & facts about biking in suburbs
    * @param g Graphics object for drawing
    */
   public void bikeScreen(Graphics g) {

      g.drawImage(chalkboard.getImage(), 100, -50, null);

      g.drawImage(bike.getImage(), 255, 65, null);
      g.drawImage(character.getImage(), 525, 140, null);
      g.setFont(new Font("Helvetica Neue", Font.BOLD, 48));
      g.setColor(Color.white);
      g.drawString("1.", 175, 120);
      messages[4].draw(g);
   }
   /**
    * Walk screen slide
    * Pros, cons & facts about walking in suburbs
    * @param g Graphics object for drawing
    */
   public void walkScreen(Graphics g) {
      g.drawImage(chalkboard.getImage(), 100, -50, null);
      g.drawImage(walk.getImage(), 285, 120, null);
      g.drawImage(character.getImage(), 525, 140, null);

      g.setFont(new Font("Helvetica Neue", Font.BOLD, 48));
      g.setColor(Color.white);
      g.drawString("2.", 175, 120);
      messages[5].draw(g);

   }
   /**
    * Bus screen slide
    * Pros, cons & facts about busing in suburbs
    * @param g Graphics object for drawing
    */
   public void busScreen(Graphics g) {
      g.drawImage(chalkboard.getImage(), 100, -50, null);
      g.drawImage(bus.getImage(), 260, 50, null);
      g.drawImage(character.getImage(), 525, 140, null);

      g.setFont(new Font("Helvetica Neue", Font.BOLD, 48));
      g.setColor(Color.white);
      g.drawString("3.", 175, 120);
      messages[6].draw(g);
   }
   /**
    * Uber screen slide
    * Pros, cons & facts about ubering in suburbs
    * @param g Graphics object for drawing
    */
   public void uberScreen(Graphics g) {
      g.drawImage(chalkboard.getImage(), 100, -50, null);
      g.drawImage(car.getImage(), 260, 160, null);
      g.drawImage(character.getImage(), 525, 140, null);

      g.setFont(new Font("Helvetica Neue", Font.BOLD, 48));
      g.setColor(Color.white);
      g.drawString("4.", 175, 120);
      messages[7].draw(g);

   }
   /**
    * Solution screen slide
    * Idea of making a change to combat urban sprawl
    * @param g Graphics object for drawing
    */
   public void solutionScreen(Graphics g) {
      g.drawImage(character.getImage(), 525, 140, null);
      g.drawImage(thinking.getImage(), 185,0,null);
      g.setFont(new Font("Helvetica Neue", Font.BOLD, 48));
      g.setColor(Color.white);
      messages[8].draw(g);
   }

   /**
    * Petition screen slide
    * Gives idea to start or sign petitions that combat causes of urban sprawl
    * @param g Graphics object for drawing
    */
   public void petitionScreen(Graphics g) {
      // draw a grid of petitioners
      for (int n = 360; n < 710; n+=65) {
         g.drawImage(petitioner.getImage(), n, 130, null);
      }
      for (int n = 340; n < 690; n+=65) {
         g.drawImage(petitioner.getImage(), n, 170, null);
      }
      for (int n = 320; n < 670; n+=65) {
         g.drawImage(petitioner.getImage(), n, 210, null);
      }
      for (int n = 300; n < 650; n+=65) {
         g.drawImage(petitioner.getImage(), n, 250, null);
      }
      g.drawImage(sign.getImage(), 0,0,null);
      g.setFont(new Font("Helvetica Neue", Font.BOLD, 48));
      g.setColor(Color.white);
      messages[9].draw(g);
   }
   /**
    * End screen slide
    * Closes out learning level, transitions into maze level
    * @param g Graphics object for drawing
    */
   public void endScreen(Graphics g) {
      //g.setColor(new Color(167, 227, 232));
      //g.fillRect(0,0,800,500);
      g.drawImage(laptop.getImage(), 200, -50, null);
      g.drawImage(character.getImage(), 525, 140, null);
      messages[10].draw(g);
   }
   /**
    * Drawing class passes through various paint methods for the frame
    * using a switch statement.
    */
   class Drawing extends JComponent {
      /**
       * Paint method
       * Checks the screen the game is on currently, and paints the corresponding design. R
       * Resets screen int upon reaching last slide.
       * @param g  the <code>Graphics</code> context in which to paint
       */
      public void paint (Graphics g) {
         switch (screen) {
            case 0:
               introScreen(g);
               break;
            case 1:
               suburbsScreen(g);
               break;
            case 2:
               travelScreen(g);
               break;
            case 3:
               waysToTravelScreen(g);
               break;
            case 4:
               bikeScreen(g);
               break;
            case 5:
               walkScreen(g);
               break;
            case 6:
               busScreen(g);
               break;
            case 7:
               uberScreen(g);
               break;
            case 8:
               solutionScreen(g);
               break;
            case 9:
               petitionScreen(g);
               break;
            case 10:
               endScreen(g);
               break;
            case 11:
               screen = 0;
               Controller.mazeUnlocked = true;
               Controller.changeScreen(6);

               break;
         }
      }
   }




}