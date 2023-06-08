import java.awt.*;
import java.awt.event.*;


public class Maze implements KeyListener, MouseListener {
    private Player player;
    private Obstacle[] obstacles;
    private static boolean[] buttons = {false, false, false, false};  // up down left right
    private static boolean[] pressedButtons = {false, false, false, false};
    public static boolean[] finishedLevels = {false, false, false};
    public static Sprite[] starSprites = {new Sprite("assets/emptyStar.png", 2), new Sprite("assets/filledStar.png", 2)};
    private static final Font font = new Font("Monospaced", Font.BOLD, 10);
    private int xOffset;
    private int yOffset;
    private int dialogueIndex;
    private boolean isInDialogue;
    private char lastKeyPressed;
    private boolean sendBack;
    private MessageBox prompt;
    private WalkingGame.SampleGame walkingGame;
    private BikingGame.SampleGame bikingGame;

    public Maze() {
        player = new Player(800 / 2, 500 / 2, new Sprite[]{
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

                // top wing
                new Obstacle(50, -2350, 250, 2300),
                new Obstacle(500, -2350, 50, 2300),
        };

        dialogueIndex = 0;
        lastKeyPressed = 0;
        sendBack = false;
        walkingGame = new WalkingGame.SampleGame(-1000, -220, 1300, 150);
        bikingGame = new BikingGame.SampleGame(550, -2050, 100, 2000);
    }

    public void handleDialogue(Graphics g, MessageBox prompt) {
        isInDialogue = walkingGame.isInDialogue() || bikingGame.isInDialogue();

        if (prompt != null) {
            prompt.draw(g);
            return;
        }

        if (walkingGame.isInDialogue()) {
            if (dialogueIndex < WalkingGame.SampleGame.dialogue.length) {
                WalkingGame.SampleGame.dialogue[dialogueIndex].draw(g);
                if (WalkingGame.SampleGame.dialogue[dialogueIndex].isQuestion()) {
                    if (lastKeyPressed == '1' || lastKeyPressed == '2' || lastKeyPressed == '3') {
                        if (lastKeyPressed == WalkingGame.SampleGame.dialogue[dialogueIndex].getAnswer()) {
                            walkingGame.setReadDialogue(true);
                            dialogueIndex++;
                        } else {
                            dialogueIndex += 2;
                            sendBack = true;
                        }

                    }
                }
            } else {
                dialogueIndex = 0;
                lastKeyPressed = 0;
                walkingGame.setDialogue(false);
            }
        }

        if (bikingGame.isInDialogue()) {
            if (dialogueIndex < BikingGame.SampleGame.dialogue.length) {
                BikingGame.SampleGame.dialogue[dialogueIndex].draw(g);
                if (BikingGame.SampleGame.dialogue[dialogueIndex].isQuestion()) {
                    if (lastKeyPressed == '1' || lastKeyPressed == '2' || lastKeyPressed == '3') {
                        if (lastKeyPressed == BikingGame.SampleGame.dialogue[dialogueIndex].getAnswer()) {
                            bikingGame.setReadDialogue(true);
                            dialogueIndex++;
                        } else {
                            dialogueIndex += 2;
                            sendBack = true;
                        }

                    }
                }
            } else {
                dialogueIndex = 0;
                lastKeyPressed = 0;
                bikingGame.setDialogue(false);
            }
        }
    }

    public void move(int xDistance, int yDistance) {
        xOffset += xDistance;
        yOffset += yDistance;

        walkingGame.move(xDistance, yDistance);
        bikingGame.move(xDistance, yDistance);
        for (Obstacle obstacle : obstacles) {
            obstacle.moveX(xDistance);
            obstacle.moveY(yDistance);
        }
    }

    public void paint(Graphics g) {


        int[] distance = new int[] {0, 0};
        if (!isInDialogue && !bikingGame.isPlaying())
            distance = player.move(buttons, obstacles);
        if (bikingGame.isPlaying()) {
            prompt = null;
            player.bikingMove(pressedButtons, bikingGame.getCurrentLane(), bikingGame);
        }
        pressedButtons = new boolean[] {false, false, false, false};

        move(distance[0], distance[1]);

        if (walkingGame.getPrompt() != null)
            prompt = walkingGame.getPrompt();
        if (bikingGame.getPrompt() != null)
            prompt = bikingGame.getPrompt();
        if (bikingGame.draw(g, player, xOffset, yOffset, this))
            sendBack = true;
        if (walkingGame.draw(g, player))
            sendBack = true;

        if (sendBack) {  // dead or win
            walkingGame.move(-xOffset, -yOffset);
            bikingGame.move(-xOffset, -yOffset);

            for (Obstacle obstacle : obstacles) {
                obstacle.moveX(-xOffset);
                obstacle.moveY(-yOffset);
            }
            xOffset = 0;
            yOffset = 0;

            sendBack = false;
        }

        if (bikingGame.isPlaying())
            player.drawUp(g);
        else
            player.draw(g, buttons);

        for (Obstacle obstacle : obstacles) {
            obstacle.draw(g);
        }

        handleDialogue(g, prompt);

        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString("Collect all three stars by playing the games to unlock escape room", 10, 15);
        for (int i = 0; i < finishedLevels.length; i++) {
            g.drawImage(starSprites[finishedLevels[i] ? 1 : 0].getImage(), 10 + i * 40, 20, null);
        }
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
        } if (isInDialogue)
            lastKeyPressed = e.getKeyChar();
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
    public void mouseClicked(MouseEvent e) {
        walkingGame.clearPrompt();
        bikingGame.clearPrompt();
        prompt = null;
        if (isInDialogue && !WalkingGame.SampleGame.dialogue[dialogueIndex].isQuestion() && !BikingGame.SampleGame.dialogue[dialogueIndex].isQuestion())
            dialogueIndex += WalkingGame.SampleGame.dialogue[dialogueIndex].getChange();
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
}
