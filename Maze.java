import java.awt.*;
import java.awt.event.*;


public class Maze implements KeyListener {
    private Player player;
    private Obstacle[] obstacles;
    private static boolean[] buttons = {false, false, false, false};  // up down left right

    private WalkingGame.SampleGame walkingGame;
    public Maze() {
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
                new Obstacle(50, 50, 200, 100)
        };

        walkingGame = new WalkingGame.SampleGame(0, 0, 700, 150);
    }
    public void paint(Graphics g) {



        int[] distance = player.move(buttons, obstacles);
        walkingGame.move(distance[0], distance[1]);

        walkingGame.draw(g);
        player.draw(g, buttons);

        for (Obstacle obstacle : obstacles) {
            obstacle.moveX(distance[0]);
            obstacle.moveY(distance[1]);
            obstacle.draw(g);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
            buttons[0] = true;
        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
            buttons[1] = true;
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
            buttons[2] = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
            buttons[3] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
            buttons[0] = false;
        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
            buttons[1] = false;
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
            buttons[2] = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
            buttons[3] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
