import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LevelSelect implements MouseListener, MouseMotionListener {

    int xHover;
    int yHover;
    int xClick;
    int yClick;


    public LevelSelect() {

        xHover = 0;
        yHover = 0;
        xClick = 0;
        yClick = 0;
    }

    public void mouseClicked (MouseEvent e) {
        if (xHover < 120 && yHover > 415)
            Controller.changeScreen(1);
        if (xHover>250 && xHover<550 && yHover>150 && yHover < 225)
            Controller.changeScreen(7);
        if ((xHover>250 && xHover<550 && yHover>255 && yHover < 330) && (Controller.mazeUnlocked == true))
            Controller.changeScreen(3);
        if ((xHover>250 && xHover<550 && yHover>360 && yHover < 435) && (Controller.escapeUnlocked == true))
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
        g.drawString("Level Select", 277, 80);

        g.setColor(Color.white);

        g.fillRect(250, 125, 300, 75);
        g.fillRect(250, 230 ,300, 75);
        g.fillRect(250, 335, 300, 75);

        g.setColor(new Color(209, 196, 56));
        if (xHover>250 && xHover<550 && yHover>150 && yHover < 225)
            g.fillRect(250, 125, 300, 75);
        if (xHover>250 && xHover<550 && yHover>255 && yHover < 330)
            g.fillRect(250, 230, 300, 75);
        if (xHover>250 && xHover<550 && yHover>360 && yHover < 435)
            g.fillRect(250, 335, 300, 75);
        g.setColor(Color.black);
        g.setFont(new Font("Helvetica Neue", Font.PLAIN, 24));
        g.drawString("1. Learning", 300, 170);

        if (Controller.mazeUnlocked == false) {
            g.setColor(new Color(48, 48, 48));
            g.fillRect(250,230,300,75);
            g.setFont(new Font("Helvetica Neue", Font.PLAIN, 27));
            g.setColor(Color.white);
            g.drawString("Locked", 300, 277);
        } else {
            g.drawString("2. Maze", 300, 275);
        }

        if (Controller.escapeUnlocked == false) {
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