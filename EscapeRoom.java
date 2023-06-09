import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EscapeRoom implements KeyListener, MouseListener {
    private static boolean[] buttons = {false, false, false, false};  // up down left right
    private static boolean[] pressedButtons = {false, false, false, false};
    private Player player;
    private Obstacle[] obstacles;
    private Obstacle[] background;
    private Obstacle[] foreground;
    private Teacher walkPerson;
    private Teacher bikePerson;
    private Teacher busPerson;
    private WalkingGame walkGame;
    private BikingGame bikeGame;
    private BusGame busGame;
    private int walkHighScore;
    private int bikeHighScore;
    private int busHighScore;
    private int xOffset;
    private int yOffset;
    private String game;

    public EscapeRoom() {
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
            },
            new Sprite[]{
                    new Sprite("assets/rebeccaBiking0.png"),
                    new Sprite("assets/rebeccaBiking1.png"),
            });

        obstacles = new Obstacle[] {
                new Obstacle(-300, 70, 50, 330),  // left
                new Obstacle(-300, 70, 2000, 50),  // top
                new Obstacle(1050, 70, 50, 330),  // right
                new Obstacle(-300, 400, 2000, 50),  // bottom
        };

        background = new Obstacle[] {
                new Obstacle(-800, 0, 2300, 200, new Color(117, 232, 100)),  // upper grass
                new Obstacle(-800, 200, 2300, 200, new Color(120, 120, 120)),  // main road
                new Obstacle(-800, 400, 2300, 200, new Color(117, 232, 100)),  // lower grass
                new Obstacle(-800, 200, 2300, 20, new Color(150, 150, 150)),  // upper curb
                new Obstacle(-800, 380, 2300, 20, new Color(150, 150, 150)),  // lower curb

                new Obstacle(500, -300, 200, 520, new Color(120, 120, 120)),  // side road
                new Obstacle(500, -300, 20, 520, new Color(150, 150, 150)),  // left curb
                new Obstacle(700, -300, 20, 520, new Color(150, 150, 150)),  // right curb

                new Obstacle(0, -50, new Sprite("assets/blueHouse.png", 8)),
                new Obstacle(-300, -50, new Sprite("assets/redHouse.png", 8)),
                new Obstacle(-600, -50, new Sprite("assets/orangeHouse.png", 8)),
                new Obstacle(300, 0, new Sprite("assets/treetest.png", 12)),
                new Obstacle(330, 105, new Sprite("assets/bike.png", 3)),
                new Obstacle(800, -50, new Sprite("assets/blueHouse.png", 8)),
                new Obstacle(1100, -50, new Sprite("assets/redHouse.png", 8)),
                new Obstacle(1400, -50, new Sprite("assets/orangeHouse.png", 8)),

        };

        foreground = new Obstacle[] {
                new Obstacle(-350, 335, new Sprite("assets/car.png")),
                new Obstacle(200, 335, new Sprite("assets/car.png")),
                new Obstacle(1000, 335, new Sprite("assets/car.png")),
                new Obstacle(100, 350, new Sprite("assets/treetest.png", 12)),
                new Obstacle(-200, 390, new Sprite("assets/treetest.png", 10)),
                new Obstacle(900, 370, new Sprite("assets/treetest.png", 11)),
                new Obstacle(-700, 370, new Sprite("assets/treetest.png", 11)),
                new Obstacle(1300, 410, new Sprite("assets/treetest.png", 10)),

        };

        game = "DEFAULT";

        walkPerson = new Teacher(-350, 200, new Sprite("assets/walkingMan.png", 7),
                new String[] {"CLICK TO PLAY WALK GAME", "> HIGHSCORE: "});  // TODO ADD HIGH SCORE
        bikePerson = new Teacher(555, 50, new Sprite("assets/bikingMan.png", 7),
                new String[] {"CLICK TO PLAY BIKE GAME", "> HIGHSCORE: "});
        busPerson = new Teacher(1050, 200, new Sprite("assets/BusMan.png", 7),
                new String[] {"CLICK TO PLAY BUS GAME", "> HIGHSCORE: "});


    }

    public void move(int xDistance, int yDistance) {
        xOffset += xDistance;
        yOffset += yDistance;

        for (Obstacle obstacle : obstacles) {
            obstacle.moveX(xDistance);
            obstacle.moveY(yDistance);
        }
        for (Obstacle obstacle : background) {
            obstacle.moveX(xDistance);
            obstacle.moveY(yDistance);
        }
        for (Obstacle obstacle : foreground) {
            obstacle.moveX(xDistance);
            obstacle.moveY(yDistance);
        }
        walkPerson.moveX(xDistance);
        walkPerson.moveY(yDistance);
        bikePerson.moveX(xDistance);
        bikePerson.moveY(yDistance);
        busPerson.moveX(xDistance);
        busPerson.moveY(yDistance);
    }

    public void updateDefault(Graphics g) {
        g.setColor(new Color(93, 199, 77));
        g.fillRect(0, 0, 800, 500);

        int[] distance = player.move(buttons, obstacles);
        pressedButtons = new boolean[] {false, false, false, false};

        move(distance[0], distance[1]);
        for (Obstacle obstacle : background)
            obstacle.draw(g);

        walkPerson.draw(g);
        bikePerson.draw(g);
        busPerson.draw(g);

        player.draw(g, buttons);
        for (Obstacle obstacle : foreground)
            obstacle.draw(g);

        walkPerson.paintPrompt(g, player);
        bikePerson.paintPrompt(g, player);
        busPerson.paintPrompt(g, player);
    }

    public void updateWalkGame(Graphics g) {
        int[] distance = player.move(buttons, walkGame.getObstacles());
        if (xOffset < -500)
            distance[0] += Player.SPEED;
        if (xOffset > 500)
            distance[0] -= Player.SPEED;
        if (yOffset < Math.max(0,  walkGame.getScore() - 200))
            distance[1] += Player.SPEED;

        xOffset += distance[0];
        yOffset += distance[1];
        pressedButtons = new boolean[] {false, false, false, false};


        walkGame.move(distance[0], distance[1]);

        walkGame.paint(g, yOffset, player);
        player.draw(g, buttons);
    }

    public void updateBikeGame(Graphics g) {

    }

    public void updateBusGame(Graphics g) {

    }

    public void paint(Graphics g) {
        if (game.equals("DEFAULT"))
            updateDefault(g);
        if (game.equals("WALK"))
            updateWalkGame(g);
        if (game.equals("BIKE"))
            updateBikeGame(g);
        if (game.equals("BUS"))
            updateBusGame(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            buttons[0] = true;
            pressedButtons[0] = true;
        } if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            buttons[1] = true;
            pressedButtons[1] = true;
        } if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            buttons[2] = true;
            pressedButtons[2] = true;
        } if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            buttons[3] = true;
            pressedButtons[3] = true;
        }
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

    @Override
    public void mouseClicked(MouseEvent e) {
        if (walkPerson.inRadius(player)) {
            walkGame = new WalkingGame();
            xOffset = 0;
            yOffset = 0;
            game = "WALK";
        }
        else if (bikePerson.inRadius(player)) {
            game = "BIKE";
        }

        else if (busPerson.inRadius(player)) {
            game = "BUS";
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
