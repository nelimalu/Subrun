import java.awt.*;
import java.awt.event.*;

/**
 * Maze class handles and draws the Maze Room. It manages between three sample levels
 *
 * <strong>Course info:</strong>
 * ICS4U0 with V. Krasteva
 *
 * @version 1.0
 * @author [70%] Luka Jovanovic & [30%] Brian Song
 * Luka: maze game, escape room game, bike game, draw sprites, movement
 * Brian: npc interaction, dialogue
 * Created on 2023/06/03
 */
public class Maze implements KeyListener, MouseListener, MouseMotionListener {
    /** player object that the user interacts with */
    private Player player;

    /** collidable obstacles that keeps the user in the bounds of the maze */
    private Obstacle[] obstacles;

    /** four booleans representing if each of the up, down, left, and right keys are currently being held respectively */
    private static boolean[] buttons = {false, false, false, false};  // up down left right

    /** four booleans representing if each of the up, down, left, and right keys are currently being pressed respectively */
    private static boolean[] pressedButtons = {false, false, false, false};

    /** three booleans representing if each of the three sample games have been completed */
    public static boolean[] finishedLevels = {false, false, false};

    /** sprites used to visualize the finished levels */
    public static Sprite[] starSprites = {new Sprite("assets/emptyStar.png", 2), new Sprite("assets/filledStar.png", 2)};

    /** sprites used to enhance the background */
    private static Obstacle[] backgroundImages;

    /** font that is used to draw text in the maze */
    private static final Font font = new Font("Monospaced", Font.BOLD, 10);

    /** X distance travelled by the player in relation to the origin */
    private int xOffset;

    /** X mouse position */
    private int xHover;

    /** Y distance travelled by the player in relation to the origin */
    private int yOffset;

    /** Y mouse position */
    private int yHover;

    /** which box in the dialogue the player is currently in */
    private int dialogueIndex;

    /** if the player is currently in a dialogue */
    private boolean isInDialogue;

    /** last keyboard key pressed by the user */
    private char lastKeyPressed;

    /** if the user should be sent back to the origin */
    private boolean sendBack;

    /** prompt to be shown after a level is complete */
    private MessageBox prompt;

    /** walking game object */
    private WalkingGame.SampleGame walkingGame;

    /** biking game object */
    private BikingGame.SampleGame bikingGame;

    /** bus game object */
    private BusGame.SampleGame busGame;

    /**
     * Maze constructor:
     * Creates a player based on selected character
     * creates obstacles to restrict player within the games
     */
    public Maze() {
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
                    }
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
                    }
            );
        }

        obstacles = new Obstacle[]{
                // start
                new Obstacle(250, 330, 250, 50),
                new Obstacle(250, 150, 50, 230),
                new Obstacle(500, 150, 50, 230),

                // halls

                // left wing
                new Obstacle(-500, 150, 800, 50),
                new Obstacle(-500, -70, 50, 250),
                new Obstacle(-1050, -70, 550, 50),
                new Obstacle(-250, -70, 550, 50),
                new Obstacle(-1050, -2350, 300, 2300),
                new Obstacle(-1050, -2350, 1100, 50),

                // right wing
                new Obstacle(500, 150, 400, 50),  // bottom one
                new Obstacle(500, -70, 150, 50),  // left side
                new Obstacle(750, -70, 150, 50),  // right side
                new Obstacle(850, -2350, 50, 2500),  // big wall
                new Obstacle(500, -2350, 400, 300),

                // top wing
                new Obstacle(50, -2350, 250, 2300),
                new Obstacle(500, -2350, 50, 2300),
                new Obstacle(-500, -2850, 50, 500),
                new Obstacle(-500, -2900, 4000, 50),
                new Obstacle(3500, -2850, 50, 500),
                new Obstacle(550, -2350, 3000, 50),
        };

        backgroundImages = new Obstacle[] {
                new Obstacle(320, -10, new Sprite("assets/arrows.png"))
        };

        dialogueIndex = 0;
        lastKeyPressed = 0;
        sendBack = false;
        walkingGame = new WalkingGame.SampleGame(-1000, -220, 1300, 150);
        bikingGame = new BikingGame.SampleGame(550, -2050, 100, 2000);
        busGame = new BusGame.SampleGame(-500, -2800, 4000, 450);
    }

    /**
     * Handles the dialogue when a player is talking to an NPC
     * Three different dialogues for each npc
     * Also handles the prompt shown after a game
     * @param g Graphics object for drawing
     * @param prompt The prompt to be displayed
     */
    public void handleDialogue(Graphics g, MessageBox prompt) {
        isInDialogue = walkingGame.isInDialogue() || bikingGame.isInDialogue() || busGame.isInDialogue();

        if (prompt != null) {
            prompt.draw(g);
            return;
        }

        if (walkingGame.isInDialogue()) {
            if (dialogueIndex < WalkingGame.SampleGame.dialogue.length) {  // if there is more dialogue
                WalkingGame.SampleGame.dialogue[dialogueIndex].draw(g);
                if (WalkingGame.SampleGame.dialogue[dialogueIndex].isQuestion()) {  // if its a question
                    if (lastKeyPressed == '1' || lastKeyPressed == '2' || lastKeyPressed == '3') {  // if the last key pressed is a valid response
                        if (lastKeyPressed == WalkingGame.SampleGame.dialogue[dialogueIndex].getAnswer()) {  // if its right
                            walkingGame.setReadDialogue(true);
                            dialogueIndex++;
                        } else {  // if its wrong
                            dialogueIndex += 2;
                            sendBack = true;
                        }

                    }
                }
            } else {  // if the dialogue is done
                dialogueIndex = 0;
                lastKeyPressed = 0;
                walkingGame.setDialogue(false);
            }
        }

        if (bikingGame.isInDialogue()) {
            if (dialogueIndex < BikingGame.SampleGame.dialogue.length) {  // if there is more dialogue
                BikingGame.SampleGame.dialogue[dialogueIndex].draw(g);
                if (BikingGame.SampleGame.dialogue[dialogueIndex].isQuestion()) {  // if its a question
                    if (lastKeyPressed == '1' || lastKeyPressed == '2' || lastKeyPressed == '3') {  // if the last key pressed is a valid response
                        if (lastKeyPressed == BikingGame.SampleGame.dialogue[dialogueIndex].getAnswer()) {  // if its right
                            bikingGame.setReadDialogue(true);
                            dialogueIndex++;
                        } else {  // if its wrong
                            dialogueIndex += 2;
                            sendBack = true;
                        }

                    }
                }
            } else {  // if the dialogue is done
                dialogueIndex = 0;
                lastKeyPressed = 0;
                bikingGame.setDialogue(false);
            }
        }

        if (busGame.isInDialogue()) {
            if (dialogueIndex < BusGame.SampleGame.dialogue.length) {  // if there is more dialogue
                BusGame.SampleGame.dialogue[dialogueIndex].draw(g);
                if (BusGame.SampleGame.dialogue[dialogueIndex].isQuestion()) {  // if its a question
                    if (lastKeyPressed == '1' || lastKeyPressed == '2' || lastKeyPressed == '3') {  // if the last key pressed is a valid response
                        if (lastKeyPressed == BusGame.SampleGame.dialogue[dialogueIndex].getAnswer()) {  // if its right
                            busGame.setReadDialogue(true);
                            dialogueIndex++;
                        } else {  // if it's wrong
                            dialogueIndex += 2;
                            sendBack = true;
                        }

                    }
                }

            } else {  // if the dialogue is done
                dialogueIndex = 0;
                lastKeyPressed = 0;
                busGame.setDialogue(false);
            }
        }
    }

    /**
     * Move the maze in relation to player movements to simulate movement
     * @param xDistance X distance travelled
     * @param yDistance Y distance tavelled
     */
    public void move(int xDistance, int yDistance) {
        xOffset += xDistance;
        yOffset += yDistance;

        walkingGame.move(xDistance, yDistance);
        bikingGame.move(xDistance, yDistance);
        busGame.move(xDistance, yDistance);
        for (Obstacle obstacle : obstacles) {
            obstacle.moveX(xDistance);
            obstacle.moveY(yDistance);
        }
        for (Obstacle obstacle : backgroundImages) {
            obstacle.moveX(xDistance);
            obstacle.moveY(yDistance);
        }
    }

    /**
     * Reset positions of everything on the map to simulate player teleporting back to the start
     */
    public void resetPositions() {
        walkingGame.move(-xOffset, -yOffset);
        bikingGame.move(-xOffset, -yOffset);
        busGame.move(-xOffset, -yOffset);

        for (Obstacle obstacle : obstacles) {
            obstacle.moveX(-xOffset);
            obstacle.moveY(-yOffset);
        }
        for (Obstacle obstacle : backgroundImages) {
            obstacle.moveX(-xOffset);
            obstacle.moveY(-yOffset);
        }
        xOffset = 0;
        yOffset = 0;
    }

    /**
     * Clear the prompt that is shown after a game is played
     */
    public void clearPrompt() {
        prompt = null;
    }

    /**
     * Main game loop for the maze
     * manages drawing each game as well as player movement between them
     * @param g Graphics object for drawing
     */
    public void paint(Graphics g) {
        int[] distance = new int[] {0, 0};
        if (!isInDialogue && !bikingGame.isPlaying() && !busGame.isPlaying())  // restrict movement during dialogues, biking game, and bus game
            distance = player.move(buttons, obstacles);
        if (bikingGame.isPlaying()) {  // movement is different in biking game
            prompt = null;
            player.bikingMove(pressedButtons, bikingGame.getCurrentLane(), bikingGame);
        }
        pressedButtons = new boolean[] {false, false, false, false};  // make pressed buttons only detect for one frame

        move(distance[0], distance[1]);  // move whole map in relation to palyer movements

        // draw prompts
        if (walkingGame.getPrompt() != null)
            prompt = walkingGame.getPrompt();
        if (bikingGame.getPrompt() != null)
            prompt = bikingGame.getPrompt();
        if (busGame.getPrompt() != null)
            prompt = busGame.getPrompt();

        for (Obstacle image : backgroundImages) {
            image.draw(g);
        }

        // decide if player should be sent back to the origin
        if (bikingGame.draw(g, player, xOffset, yOffset, this))
            sendBack = true;
        if (walkingGame.draw(g, player))
            sendBack = true;
        busGame.draw(g, player, xOffset, yOffset, this);  // draw bus game

        if (sendBack) {  // dead or win
            resetPositions();  // reset positions in case player was in bike game

            sendBack = false;  // stop sending back
        }

        // draw player differently for bike game
        if (bikingGame.isPlaying())
            player.drawUp(g);
        else if (!busGame.isPlaying())  // do not draw player in bus game
            player.draw(g, buttons);

        for (Obstacle obstacle : obstacles) {
            obstacle.draw(g);
        }

        handleDialogue(g, prompt);  // draw the dialogue

        // star message on top left
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString("Collect all three stars by playing the games to unlock escape room", 10, 15);
        for (int i = 0; i < finishedLevels.length; i++) {
            g.drawImage(starSprites[finishedLevels[i] ? 1 : 0].getImage(), 10 + i * 40, 20, null);
        }
        if (finishedLevels[0] && finishedLevels[1] && finishedLevels[2]) {  // can move on to escape level if all three samples are complete
            Controller.escapeUnlocked = true;
        }
        g.setColor(Color.white);
        g.fillRect(700,0,100,35);
        g.setFont(new Font("Arial", Font.BOLD, 25));
        g.setColor(Color.black);
        g.drawRect(700,0,100,35);
        g.drawString("Exit", 717, 25);

        if (xHover >710  && yHover < 50) {  // highlight exit button when the user hovers oer
            g.setFont(new Font("Arial", Font.BOLD, 25));
            g.setColor(new Color(135, 134, 134));
            g.drawString("Exit", 717,25);


        }
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
        } if (isInDialogue)
            lastKeyPressed = e.getKeyChar();
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
     * Handle all mouse clicks
     * Used to skip dialogue, get off at a stop during bus game, and exit maze game
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        walkingGame.clearPrompt();
        bikingGame.clearPrompt();
        busGame.clearPrompt();
        prompt = null;
        if (isInDialogue && !WalkingGame.SampleGame.dialogue[dialogueIndex].isQuestion() && !BikingGame.SampleGame.dialogue[dialogueIndex].isQuestion() && !BusGame.SampleGame.dialogue[dialogueIndex].isQuestion())
            dialogueIndex += WalkingGame.SampleGame.dialogue[dialogueIndex].getChange();

        if (busGame.isPlaying()) {
            resetPositions();
            if (busGame.isAtStop()) {
                busGame.handleWin();
                finishedLevels[2] = true;
            } else {
                busGame.handleLoss();
            }
        }

        if (xHover >710  && yHover < 50)  // click on exit button
            Controller.changeScreen(6);
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    /**
     * Handle mouse movement
     * keep track of current mouse position using xHover and yHover
     * @param e the event to be processed
     */
    public void mouseMoved (MouseEvent e) {
        xHover = e.getX();
        yHover = e.getY();
    }

    public void mouseDragged (MouseEvent e) { }

}
