import java.awt.*;
import java.awt.event.*;

/**
 * EscapeRoom class handles and draws the third Escape Room. It manages between three game levels, a
 * default level, and win + loss screens
 *
 * <strong>Course info:</strong>
 * ICS4U0 with V. Krasteva
 *
 * @version 1.0
 * @author Luka Jovanovic & Brian Song
 * Created on 2023/06/07
 */
public class EscapeRoom implements KeyListener, MouseListener, MouseMotionListener {
    /** four booleans representing if each of the up, down, left, and right keys are currently being held respectively */
    private static boolean[] buttons = {false, false, false, false};  // up down left right

    /** four booleans representing if each of the up, down, left, and right keys are currently being pressed respectively */
    private static boolean[] pressedButtons = {false, false, false, false};

    /** player object for movement */
    private Player player;

    /** obstacle objects to create level boundaries */
    private Obstacle[] obstacles;

    /** collisionless obstacle objects decorate the background */
    private Obstacle[] background;

    /** collisionless obstacle objects to decorate the foreground */
    private Obstacle[] foreground;

    /** NPC to enter the walking game */
    public Teacher walkPerson;

    /** NPC to enter the biking game */
    public Teacher bikePerson;

    /** NPC to enter the bus game */
    public Teacher busPerson;

    /** Instance of the walking game */
    private WalkingGame walkGame;

    /** Instance of the biking game */
    private BikingGame bikeGame;

    /** Instance of the bus game */
    private BusGame busGame;

    /** Keep track of the highest score in the walking game */
    private int walkHighScore;

    /** Keep track of the highest score in the biking game */
    private int bikeHighScore;

    /** Keep track of the highest score in the bus game */
    private int busHighScore;

    /** Keep track of the X distance travelled by the player */
    private int xOffset;

    /** Keep track of the Y distance travelled by the player */
    private int yOffset;

    /** Which game to render */
    private String game;

    /** X position of the mouse */
    private int xHover;

    /** Y position of the mouse */
    private int yHover;

    /**
     * Escape room constructor:
     * creates a player object based on character selected previously
     * creates borders with collision to make sure player stays in the required zone
     * creates foreground & background objects for decoration
     * creates NPCs to enter games
     */
    public EscapeRoom() {
        if (Controller.CHARACTER.equals("Rebecca")) {
            player = new Player(800 / 2, 500 / 2, new Sprite[]{
                    new Sprite("assets/rebecca0.png"),
                    new Sprite("assets/rebecca1.png"),
                    new Sprite("assets/rebecca0.png"),
                    new Sprite("assets/rebecca2.png")
            },
                    new Sprite[]{
                            new Sprite("assets/rebecca3.png"),
                            new Sprite("assets/rebecca4.png"),
                            new Sprite("assets/rebecca3.png"),
                            new Sprite("assets/rebecca5.png")
                    },
                    new Sprite[]{
                            new Sprite("assets/rebeccaBiking0.png"),
                            new Sprite("assets/rebeccaBiking1.png"),
                    },
                    new Sprite("assets/rebeccaDead.png")
            );
        } else {
            player = new Player(800 / 2, 500 / 2, new Sprite[]{
                    new Sprite("assets/benji0.png"),
                    new Sprite("assets/benji1.png"),
                    new Sprite("assets/benji0.png"),
                    new Sprite("assets/benji2.png")
            },
                    new Sprite[]{
                            new Sprite("assets/benji3.png"),
                            new Sprite("assets/benji4.png"),
                            new Sprite("assets/benji3.png"),
                            new Sprite("assets/benji5.png")
                    },
                    new Sprite[]{
                            new Sprite("assets/benjiBiking0.png"),
                            new Sprite("assets/benjiBiking1.png"),
                    },
                    new Sprite("assets/benjiDead.png")
            );
        }

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
                new String[] {"CLICK TO PLAY WALK GAME", "Reach a score of 5000 to finish this task."});
        bikePerson = new Teacher(555, 50, new Sprite("assets/bikingMan.png", 7),
                new String[] {"CLICK TO PLAY BIKE GAME", "Reach a score of 1200 to finish this task."});
        busPerson = new Teacher(1050, 200, new Sprite("assets/BusMan.png", 7),
                new String[] {"CLICK TO PLAY BUS GAME", "Get off at the right stop to finish this task."});


    }

    /**
     * Sets which game or screen to render within the escape room
     * @param game Screen to switch to
     */
    public void setGame(String game) {
        this.game = game;
    }

    /**
     * Sets the high score of the bike game
     * @param amount Amount to set the high score to
     */
    public void setBikeHighScore(int amount) {
        bikeHighScore = amount;
    }

    /**
     * Sets the high score of the walk game
     * @param amount Amount to set the high score to
     */
    public void setWalkHighScore(int amount) {
        walkHighScore = amount;
    }

    /**
     * Increments the amount of bus games won by 1
     */
    public void incrementBusHighScore() {
        busHighScore++;
    }

    /**
     * Moves all objects on the screen in relation to player movements
     * @param xDistance X distance travelled by the player
     * @param yDistance Y distance travelled by the player
     */
    public void move(int xDistance, int yDistance) {
        // keep track of distance travelled
        xOffset += xDistance;
        yOffset += yDistance;

        // move all objects individually
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

    /**
     * Game loop for the main screen where game selection happens
     * @param g Graphics object for drawing
     */
    public void updateDefault(Graphics g) {
        // consistent green background
        g.setColor(new Color(93, 199, 77));
        g.fillRect(0, 0, 800, 500);

        int[] distance = player.move(buttons, obstacles);  // get distance moved by the player
        pressedButtons = new boolean[] {false, false, false, false};  // make pressed buttons only detect for one frame

        move(distance[0], distance[1]);  // move everything on the screen
        for (Obstacle obstacle : background)
            obstacle.draw(g);  // draw each object in the background

        // draw each npc
        walkPerson.draw(g);
        bikePerson.draw(g);
        busPerson.draw(g);

        player.draw(g, buttons);  // draw player
        for (Obstacle obstacle : foreground)
            obstacle.draw(g);  // draw everything in the foreground

        // draw prompts of each npc if player is in the viscinity
        walkPerson.paintPrompt(g, player);
        bikePerson.paintPrompt(g, player);
        busPerson.paintPrompt(g, player);
    }

    /**
     * Game loop for the walking game
     * @param g Graphics object for drawing
     */
    public void updateWalkGame(Graphics g) {
        int[] distance = player.move(buttons, walkGame.getObstacles());  // get distance travelled by player
        // keep player within bounding area
        if (xOffset < -500)
            distance[0] += Player.SPEED;
        if (xOffset > 500)
            distance[0] -= Player.SPEED;
        if (yOffset < Math.max(0,  walkGame.getScore() - 200))
            distance[1] += Player.SPEED;

        // keep track of distance travelled
        xOffset += distance[0];
        yOffset += distance[1];
        pressedButtons = new boolean[] {false, false, false, false};  // make pressed buttons only detect for one frame

        walkGame.move(distance[0], distance[1]);  // move player

        walkGame.paint(g, yOffset, player, this);  // draw the game
        player.draw(g, buttons); // draw the player on top of game
    }

    /**
     * Game loop for the biking game
     * @param g Graphics object for drawing
     */
    public void updateBikeGame(Graphics g) {
        bikeGame.paint(g, player, this);  // draw the biking game
        player.bikingMove(pressedButtons, bikeGame.getCurrentLane(), bikeGame);  // move player within the lanes

        pressedButtons = new boolean[] {false, false, false, false};  // make pressed buttons only detect for one frame
        player.drawUp(g);  // draw the player, but in terms of the biking game
    }

    /**
     * Game loop for the bus game
     * @param g Graphics object for drawing
     */
    public void updateBusGame(Graphics g) {
        busGame.paint(g, this);  // draw the bus game
    }

    /**
     * Info screen after player death
     * @param g Graphics object for drawing
     */
    public void updateDead(Graphics g) {
        g.setColor(new Color(247, 163, 174));
        g.fillRect(0,0,800,500);
        g.setColor(Color.black);
        g.setFont(new Font("Helvetica Neue", Font.BOLD, 44));
        g.drawString("Sorry, you died...", 205, 75);
        g.drawString("Click anywhere to leave", 130, 400);
        g.drawString("Play again?", 265, 125);
        g.drawImage(player.getDeadSprite().scaleImage(2), 320, 165, null);
    }

    /**
     * Info screen after player death for bus game
     * @param g Graphics object for drawing
     */
    public void updateBusLoss(Graphics g) {
        g.setColor(new Color(247, 163, 174));
        g.fillRect(0,0,800,500);
        g.setColor(Color.black);
        g.setFont(new Font("Helvetica Neue", Font.BOLD, 44));
        g.drawString("Sorry, you missed your stop...", 80, 75);
        g.drawString("Click anywhere to leave", 130, 400);
        g.drawString("Try again?", 275, 125);
        g.drawImage(player.getDeadSprite().scaleImage(2), 320, 165, null);
    }

    /**
     * Info screen after player successfully completes a task
     * @param g Graphics object for drawing
     */
    public void updateAlive(Graphics g) {
        g.setColor(new Color(194, 247, 163));
        g.fillRect(0,0,800,500);
        g.setColor(Color.black);
        g.setFont(new Font("Helvetica Neue", Font.BOLD, 44));
        g.drawString("You have completed this task!", 65, 75);
        g.drawString("Click anywhere to leave", 130, 400);
        g.drawString("Congrats!", 290, 125);
        g.drawImage(player.getSprite().scaleImage(2), 320, 165, null);
    }

    /**
     * Info screen after player finishes all tasks
     * @param g Graphics object for drawing
     */
    public void finishGame(Graphics g) {
        g.setColor(new Color(194, 247, 163));
        g.fillRect(0,0,800,500);
        g.setColor(Color.black);
        g.setFont(new Font("Helvetica Neue", Font.BOLD, 44));
        g.drawString("You completed all tasks", 140, 75);
        g.drawString("and made it out of the suburbs!", 70, 125);
        g.drawImage(player.getSprite().scaleImage(2), 320, 165, null);
        g.setColor(Color.white);

        // fill buttons
        g.fillRect(125,355,230,75);
        g.fillRect(425,355,230,75);
        g.setFont(new Font("Helvetica Neue", Font.PLAIN, 24));

        g.setColor(new Color(201, 201, 201));
        if (xHover>125 && xHover<355 && yHover>375 && yHover < 450) {  // Highlight EXIT button on hover
            g.fillRect(125, 355, 230, 75);
        }
        if (xHover>425 && xHover<655 && yHover>375 && yHover < 450) {  // Highlight MENU button on hover
            g.fillRect(425, 355, 230, 75);
        }

        g.setColor(Color.black);
        g.drawString("Exit", 215, 400);
        g.drawString("Menu", 515, 400);

        // outline buttons
        g.drawRect(125,355,230,75);
        g.drawRect(425,355,230,75);
    }

    /**
     * Controlling method to manage which frame to draw
     * @param g Graphics object for drawing
     */
    public void paint(Graphics g) {
        if (busHighScore > 0 && bikeHighScore >= 1200 && walkHighScore >= 5000)  // if all tasks are completed
            finishGame(g);
        else if (game.equals("DEFAULT"))
            updateDefault(g);
        else if (game.equals("WALK"))
            updateWalkGame(g);
        else if (game.equals("BIKE"))
            updateBikeGame(g);
        else if (game.equals("BUS"))
            updateBusGame(g);
        else if (game.equals("DEAD"))
            updateDead(g);
        else if (game.equals("ALIVE"))
            updateAlive(g);
        else if (game.equals("BUSLOSS"))
            updateBusLoss(g);
    }

    /**
     * Handle all key presses
     * When a key is pressed down, the corresponding boolean is set to true. It remains true until the button is released
     * @param e the event to be processed
     */
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

    /**
     * Handle all key releases
     * When a key is pressed down, the corresponding boolean is set to true. It remains true until the button is released
     * @param e the event to be processed
     */
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

    /**
     * Handle all keys typed
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Handle all mouse clicks
     * Used to enter minigames, restarting after death, and selecting EXIT or MENU when all tasks are completed
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (busHighScore > 0 && bikeHighScore >= 1200 && walkHighScore >= 5000) {  // all tasks completed
            if (xHover > 125 && xHover < 355 && yHover > 375 && yHover < 450) {  // EXIT
                Controller.changeScreen(4);  // exit screen
            }
            if (xHover > 425 && xHover < 655 && yHover > 375 && yHover < 450) {  // MENU
                Controller.changeScreen(0);  // menu screen
            }
        }
        if (game.equals("ALIVE") || game.equals("DEAD") || game.equals("BUSLOSS")) {  // task completed or died
            player.resetPosition();
            game = "DEFAULT";  // go back to default escape room
        }
        else if (game.equals("BUS")) {  // if you click while in the bus game, it registers as exiting the bus
            busGame.checkWin(this);
        }
        // if you click near an npc, you enter the game
        else if (walkPerson.inRadius(player)) {
            walkGame = new WalkingGame();
            xOffset = 0;
            yOffset = 0;
            game = "WALK";
        }
        else if (bikePerson.inRadius(player)) {
            bikeGame = new BikingGame();
            player.resetAnimationCount();
            xOffset = 0;
            yOffset = 0;
            game = "BIKE";
        }

        else if (busPerson.inRadius(player)) {
            busGame = new BusGame();
            game = "BUS";
        }


    }

    /**
     * Handles all mouse presses
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Handles all mouse releases
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Handles all mouse enters
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Handles all mouse exits
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Handles all mouse drags
     * @param e the event to be processed
     */
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    /**
     * Handles all mouse moves
     * @param e the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        xHover = e.getX();
        yHover = e.getY();
    }
}
