import java.awt.*;
import java.util.ArrayList;

/**
 * BikingGame class handles and draws the biking game, both in the escape room and maze room
 * Inner classes are used for handling repetitiveness within the class
 *
 * <strong>Course info:</strong>
 * ICS4U0 with V. Krasteva
 *
 * @version 1.0
 * @author Luka Jovanovic & Brian Song
 * Created on 2023/06/07
 */
public class BikingGame {

    /** Array to hold all three lanes */
    private Lane[] lanes;

    /** Array to hold all cars falling down the screen */
    private ArrayList<Vehicle> vehicles;

    /** Which lane is the player currently in */
    private int currentLane;

    /** integer that is added to each frame */
    private int frame;

    /** Font used when drawing text in this class */
    private static Font scoreFont = new Font("Monospaced", Font.BOLD, 20);

    /**
     * BikingGame constructor
     * creates all three lanes and defines colours, position, and spawn rates
     */
    public BikingGame() {
        int x = 255;
        int y = -300;
        currentLane = 1;
        frame = 0;

        vehicles = new ArrayList<Vehicle>();
        lanes = new Lane[] {
                new Lane(x, y, 100, 1000, new Color(70, 70, 70), 0.005),
                new Lane(x + 100, y, 100, 1000, new Color(100, 100, 100), 0.015),  // middle lane has a higher spawn rate
                new Lane(x + 200, y, 100, 1000, new Color(70, 70, 70), 0.005),
        };
    }

    /**
     * Main game loop for the biking game in the escape room
     * handles updates and graphics
     * @param g Graphics object for drawing
     * @param player The player
     * @param room The escape room object
     */
    public void paint(Graphics g, Player player, EscapeRoom room) {
        frame++;  // increment frame

        for (Lane lane : lanes) {  // draw and update each lane
            lane.draw(g);
            lane.updateReal(vehicles);
        }

        for (Vehicle vehicle : vehicles) {  // draw each car
            vehicle.draw(g);
        }

        ArrayList<Integer> toRemove = new ArrayList<Integer>();
        for (int i = 0; i < vehicles.size(); i++) {
            vehicles.get(i).draw(g);  // draw car
            if (player.collideSmall(vehicles.get(i))) {  // check if player collides with car
                room.setGame("DEAD");
                room.setBikeHighScore(frame);
                if (frame >= 1200) {  // did the player complete the task
                    room.setGame("ALIVE");
                    room.setBikeHighScore(frame);
                    room.bikePerson.getMessage().setTextArray("You have successfully completed this task! (score > 1200)", 1);  // change message on npc
                }
                player.resetPosition();

            }
            if (vehicles.get(i).move(800)) // deleted cars that are out of bounds to save memory
                toRemove.add(i);
        }

        // delete useless cars
        for (int i = 0; i < toRemove.size(); i++)
            toRemove.set(i, toRemove.get(i) - i);

        for (int index : toRemove) vehicles.remove(index);

        // display score on top left
        g.setColor(Color.BLACK);
        g.setFont(scoreFont);
        g.drawString("Score: " + frame, 10, 30);
    }

    /**
     * get the lane the player is currently in
     * @return The current lane
     */
    public int getCurrentLane() {
        return currentLane;
    }

    /**
     * Changes which lane the player is located in
     * @param amount The lane the player should be in
     */
    public void setCurrentLane(int amount) {
        currentLane = amount;
    }

    /**
     * Inner Lane class
     * each lane class is individually drawn and individually spawns cars
     * extends Obstacle to simplify drawing
     */
    private static class Lane extends Obstacle {

        /** the colour of the lane */
        private Color colour;

        /** the rate at which cars spawn */
        private double rate;

        /**
         * Constructor for biking lanes
         * @param x
         * @param y
         * @param width
         * @param height
         * @param colour
         * @param rate The rate at which cars spawn in the lane
         */
        public Lane(int x, int y, int width, int height, Color colour, double rate) {
            super(x, y, width, height);
            this.colour = colour;
            this.rate = rate;
        }

        /**
         * Move the lane based on player movements
         * @param xDistance X distance travelled
         * @param yDistance Y distance travelled
         */
        public void move(int xDistance, int yDistance) {
            moveX(xDistance);
            moveY(yDistance);
        }

        /**
         * Draw the lane with the corresponding colour
         * @param g Graphics object for drawing
         */
        public void draw(Graphics g) {
            g.setColor(colour);
            g.fillRect(getX(), getY(), getWidth(), getHeight());
        }

        /**
         * Creates new cars based on the spawn rate
         * @param vehicles All vehicles in the game
         * @param playing Is the player playing this game?
         */
        public void update(ArrayList<Vehicle> vehicles, boolean playing) {
            if (Math.random() < rate && playing) {
                Sprite sprite = new Sprite("assets/frontFacingCar.png", 3);
                int speed = (int) (Math.random() * 5) + 5;
                vehicles.add(new Vehicle(getX() + 5, getY(), speed, sprite));
            }
        }

        /**
         * Updates the biking game for the escape room version
         * Creates new vehicles based on spawn rate
         * increases spawn rate in the lane
         * @param vehicles All vehicles in the game
         */
        public void updateReal(ArrayList<Vehicle> vehicles) {
            if (Math.random() < rate) {
                Sprite sprite = new Sprite("assets/frontFacingCar.png", 3);
                int speed = (int) (Math.random() * 5) + 5;
                vehicles.add(new Vehicle(getX() + 5, getY(), speed, sprite));
            }
            rate += 0.000003;
        }
    }

    /**
     * Inner Vehicle class
     * each car is individually drawn and moved
     */
    private static class Vehicle extends Obstacle {
        /** speed at which the car moves */
        private int speed;

        /**
         * Constructor for Vehicle class
         * @param x X position
         * @param y Y position
         * @param speed Speed at which the car moves
         * @param sprite Sprite to draw
         */
        public Vehicle(int x, int y, int speed, Sprite sprite) {
            super(x, y, sprite);
            this.speed = speed;
        }

        /**
         * Moves the car based on the speed, and decides if the car should be deleted
         * @param y Y value at which the car should be deleted
         * @return Returns if the car should be deleted or not
         */
        public boolean move(int y) {
            moveY(speed);

            return getY() > y;
        }
    }

    /**
     * Sample game that is placed in the Maze room
     * Can be moved around like an obstacle
     */
    public static class SampleGame {
        /**  */
        private Lane[] lanes;
        private ArrayList<Vehicle> vehicles;
        private Teacher teacher;
        private Teacher enterRadius;
        private int y;
        private long frame;
        private boolean readDialogue;
        private boolean inDialogue;
        private boolean playing;
        private int currentLane;
        private MessageBox prompt;

        public static final MessageBox[] dialogue = new MessageBox[] {
                new MessageBox("Howdy! Welcome to the testing level for biking in the suburbs: aka surviving as a cyclist."),
                new MessageBox("In this game mode, you must use left and right arrow keys to bike on a car filled road and make it to your destination."),
                new MessageBox("If you do get hit, you \"die\" and are sent back to the beginning."),
                new MessageBox("Before I let you try to bike through the suburbs yourself, I have one quick question for you..."),
                new MessageBox("In which location is it illegal to cycle?",
                        new String[] {
                                "1) Road",
                                "2) Sidewalk",
                                "3) Bike Lane"
                        }, '2'),
                new MessageBox("Correct! In Canada, it is illegal for a cyclist to bike on a sidewalk.", 2),
                new MessageBox("Wrong! Feel free to try again!"),
        };
        public SampleGame(int x, int y, int width, int height) {
            lanes = new Lane[] {
                    new Lane(x, y, 100, 2000, new Color(70, 70, 70), 0.005),
                    new Lane(x + 100, y, 100, 2000, new Color(100, 100, 100), 0.015),
                    new Lane(x + 200, y, 100, 2000, new Color(70, 70, 70), 0.005),
            };
            this.y = y + height;
            readDialogue = false;
            inDialogue = false;
            playing = false;
            currentLane = 1;
            frame = 0;
            vehicles = new ArrayList<Vehicle>();
            teacher = new Teacher(655, -80, new Sprite("assets/bikingMan.png", 6));
            enterRadius = new Teacher(655, -150);
        }

        public boolean isInDialogue() {
            return inDialogue;
        }

        public void setDialogue(boolean set) {
            inDialogue = set;
        }
        public void setReadDialogue(boolean set) {
            readDialogue = set;
        }

        public int getCurrentLane() {
            return currentLane;
        }

        public void setCurrentLane(int amount) {
            currentLane = amount;
        }
        public MessageBox getPrompt() {
            return prompt;
        }
        public void clearPrompt() {
            prompt = null;
        }
        public boolean isPlaying() { return playing; }

        public void move(int xDistance, int yDistance) {

            for (BikingGame.Vehicle vehicle : vehicles) {
                vehicle.moveX(xDistance);
                vehicle.moveY(yDistance);
            }

            for (BikingGame.Lane lane : lanes) {
                lane.move(xDistance, yDistance);
            }
            teacher.moveX(xDistance);
            teacher.moveY(yDistance);
            enterRadius.moveX(xDistance);
            enterRadius.moveY(yDistance);

            y += yDistance;
        }

        public boolean draw(Graphics g, Player player, int xOffset, int yOffset, Maze maze) {


            if (frame > 750) {
                playing = false;
                frame = 0;
                player.resetPosition();
                Maze.finishedLevels[1] = true;
                prompt = new MessageBox("Congratulations! You made it to your destination!");
                return true;
            }

            if (teacher.inRadius(player)) {
                if (!readDialogue)
                    inDialogue = true;
            }
            if (!playing && enterRadius.inRadius(player)) {
                clearPrompt();
                frame = 0;
                playing = true;
                player.resetAnimationCount();
                maze.move(-295 - xOffset, 0);
            }

            if (playing) {
                frame++;
                if (yOffset < 600)
                    maze.move(0, 5);
            }

            for (Lane lane : lanes) {
                lane.draw(g);
                lane.update(vehicles, playing);
            }

            ArrayList<Integer> toRemove = new ArrayList<Integer>();
            for (int i = 0; i < vehicles.size(); i++) {
                vehicles.get(i).draw(g);
                if (player.collideSmall(vehicles.get(i))) {
                    playing = false;
                    player.resetPosition();
                    vehicles.clear();
                    currentLane = 1;
                    prompt = new MessageBox("You got hit by a car! I guess the streets aren't that safe after all...");
                    return true;
                }
                if (vehicles.get(i).move(y - 30)) // out of bounds
                    toRemove.add(i);
            }

            for (int i = 0; i < toRemove.size(); i++)
                toRemove.set(i, toRemove.get(i) - i);

            for (int index : toRemove) vehicles.remove(index);

            teacher.draw(g);
            return false;
        }
    }

}
