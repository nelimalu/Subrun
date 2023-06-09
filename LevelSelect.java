import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * LevelSelect allows the user to navigate between the 3 different
 * levels in the game. The latter 2 are only unlocked as preconditions
 * are met.
 *
 * <strong>Course info:</strong>
 * ICS4U0 with V. Krasteva
 *
 * @version 1.0
 * @author [30%] Luka Jovanovic & [70%] Brian Song
 * Luka: transition into the 3 levels
 * Brian: buttons, hover, paint graphics
 * Created on 2023/06/04
 */
public class LevelSelect implements MouseListener, MouseMotionListener {
    /** stores x value of the mouse in real-time as it moves for interactive button hovers **/
    int xHover;
    /** stores y value of the mouse in real-time as it moves for interactive button hovers **/
    int yHover;
    /** stores x value on mouse click for button interaction **/
    int xClick;
    /** stores y value on mouse click for button interaction **/
    int yClick;

    /**
     * LevelSelect constructor
     * sets mouse variables to default values
     */
    public LevelSelect() {

        xHover = 0;
        yHover = 0;
        xClick = 0;
        yClick = 0;
    }
    /**
     * Handle all mouse clicks
     * opens the level that the mouse clicks on if the level is unlocked
     * goes back to character select if back is pressed
     *
     * @param e the event to be processed
     */
    public void mouseClicked (MouseEvent e) {
        if (xHover < 120 && yHover > 415)
            Controller.changeScreen(1);
        if (xHover>250 && xHover<550 && yHover>150 && yHover < 225)
            Controller.changeScreen(7);
        if ((xHover>250 && xHover<550 && yHover>255 && yHover < 330) && (Controller.mazeUnlocked))
            Controller.changeScreen(3);
        if ((xHover>250 && xHover<550 && yHover>360 && yHover < 435) && (Controller.escapeUnlocked))
            Controller.changeScreen(8);
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
     * Stores all mouse movements
     * Tracks coordinates of mouse in real time
     *
     * @param e the event to be processed
     */
    public void mouseMoved (MouseEvent e) {
        xHover = e.getX();
        yHover = e.getY();
    }

    public void mouseDragged (MouseEvent e) {
    }

    /**
     * paint method to draw the level select interface
     * locked levels will be grayed out and unhoverable until preconditions are met
     *
     * @param g graphics object to be drawn
     */
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
        g.drawString("Level Select", 277, 80);

        g.setColor(Color.white);

        g.fillRect(250, 125, 300, 75);
        g.fillRect(250, 230 ,300, 75);
        g.fillRect(250, 335, 300, 75);

        g.setColor(new Color(209, 196, 56));
        // highlight buttons that are hovered over
        if (xHover>250 && xHover<550 && yHover>150 && yHover < 225)
            g.fillRect(250, 125, 300, 75);
        if (xHover>250 && xHover<550 && yHover>255 && yHover < 330)
            g.fillRect(250, 230, 300, 75);
        if (xHover>250 && xHover<550 && yHover>360 && yHover < 435)
            g.fillRect(250, 335, 300, 75);
        g.setColor(Color.black);
        g.setFont(new Font("Helvetica Neue", Font.PLAIN, 24));
        g.drawString("1. Learning", 300, 170);

        // locking function for maze level
        if (!Controller.mazeUnlocked) {
            g.setColor(new Color(48, 48, 48));
            g.fillRect(250,230,300,75);
            g.setFont(new Font("Helvetica Neue", Font.PLAIN, 27));
            g.setColor(Color.white);
            g.drawString("Locked", 300, 277);
        } else {
            g.drawString("2. Maze", 300, 275);
        }

        // locking function for escape room
        if (!Controller.escapeUnlocked) {
            g.setColor(new Color(48, 48, 48));
            g.fillRect(250,335,300,75);
            g.setFont(new Font("Helvetica Neue", Font.PLAIN, 27));
            g.setColor(Color.white);
            g.drawString("Locked", 300, 382);
        } else {
            g.drawString("3. Escape Room", 300, 380);
        }

        g.setColor(Color.black);
        g.drawRect(250,125,300,75);
        g.drawRect(250,230,300,75);
        g.drawRect(250,335,300,75);


        // back button
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(Color.white);
        g.drawString("<- Back", 15, 440);
        if (xHover < 120 && yHover > 415) {
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.setColor(Color.yellow);
            g.drawString("<- Back", 15, 440);


        }

    }
}