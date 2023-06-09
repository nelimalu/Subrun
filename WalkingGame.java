import com.sun.security.jgss.GSSUtil;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.awt.*;

/**
 * WalkingGame class handles and draws the walking game, both in the escape room and maze room
 * Inner classes are used for handling repetitiveness within the class
 *
 * <strong>Course info:</strong>
 * ICS4U0 with V. Krasteva
 *
 * @version 1.0
 * @author Luka Jovanovic & Brian Song
 * Created on 2023/06/04
 */
public class WalkingGame {

    /** arraylist to store each lane in the game */
    private ArrayList<Lane> lanes;

    /** arraylist to store each car in the game */
    private ArrayList<Car> cars;

    /** width of the game */
    private static int WIDTH = 2000;

    /** height of the game */
    private static int HEIGHT = 150;

    /** obstacles to keep the player within the bounds of the game */
    private Obstacle[] obstacles;

    /** current score of the player, measured by distance travelled up the screen */
    private int score;

    /** font used for drawing in this class */
    private static Font scoreFont = new Font("Monospaced", Font.BOLD, 20);

    /**
     * Constructor for the walking game in the escape room
     * creates the lanes, with three grass and one road guaranteed
     */
    public WalkingGame() {
        this.lanes = new ArrayList<Lane>();
        this.cars = new ArrayList<Car>();
        this.obstacles = new Obstacle[] {};

        for (int i = 0; i < 2; i++) {
            lanes.add(new Lane(-600, 330 - i * HEIGHT, WIDTH, HEIGHT));
        }
        lanes.add(new Lane(-600, 330 - 2 * HEIGHT, WIDTH, HEIGHT, randint(1, 3) / 100.0));

        for (int i = 3; i < 50; i++) {
            if (Math.random() < 0.7)
                lanes.add(new Lane(-600, 330 - i * HEIGHT, WIDTH, HEIGHT, randint(1, 3) / 100.0));
            else
                lanes.add(new Lane(-600, 330 - i * HEIGHT, WIDTH, HEIGHT));
        }
    }

    /**
     * Main game loop for the biking game in the escape room
     * handles updates and graphics
     * @param g Graphics object for drawing
     * @param yOffset Y distance travelled by the player while in the game
     * @param player the player
     * @param room the escape room object
     */
    public void paint(Graphics g, int yOffset, Player player, EscapeRoom room) {
        ArrayList<Lane> copyLanes = new ArrayList<>(lanes);
        for (Lane lane : lanes) {
            lane.draw(g);
            lane.updateReal(cars);  // create new cars based on spawn rates

            if (lane.getY() > 1000) {  // check if the lane is off the screen
                // move the lane to the top: creates a cycle of reusing lanes to save memory
                lane.setY(lanes.get(lanes.size() - 1).getY() - HEIGHT);
                lane.setFrequency(lane.getFrequency() + 0.01);
                copyLanes.remove(0);
                copyLanes.add(lane);
            }
        }
        // copy so we can send lanes to the front without disturbing the for loop
        lanes = copyLanes;

        ArrayList<Integer> toRemove = new ArrayList<Integer>();
        for (int i = 0; i < cars.size(); i++) {
            cars.get(i).draw(g);
            if (player.collide(cars.get(i))) {  // check if the player collided with a car
                room.setGame("DEAD");
                if (score >= 5000) {  // if the player completed the task
                    room.setGame("ALIVE");
                    room.setWalkHighScore(score);  // set new high score
                    room.walkPerson.getMessage().setTextArray("You have successfully completed this task! (score > 5000)", 1);  // change message in the npc
                }
            }
            if (cars.get(i).move(-700, WIDTH - 100))  // if the car is off the screen
                toRemove.add(i);  // remove the car
        }

        // funky copying to delete useless cars non-destructively
        for (int i = 0; i < toRemove.size(); i++)
            toRemove.set(i, toRemove.get(i) - i);

        for (int index : toRemove) cars.remove(index);

        if (yOffset > score)  // increase score when the player moves up the screen (does not count moving down and back up)
            score = yOffset;

        // draw current score on top left of screen
        g.setColor(Color.BLACK);
        g.setFont(scoreFont);
        g.drawString("Score: " + score, 10, 30);
    }

    /**
     * Moves the lanes and cars when the player moves
     * @param xDistance X distance travelled
     * @param yDistance Y distance travelled
     */
    public void move(int xDistance, int yDistance) {
        for (Car car : cars) {
            car.moveX(xDistance);
            car.moveY(yDistance);
        }

        for (Lane lane : lanes) {
            lane.move(xDistance, yDistance);
        }
    }

    /**
     * @return obstacles in the game used as boundaries
     */
    public Obstacle[] getObstacles() {
        return obstacles;
    }

    /**
     * @return Player's current score
     */
    public int getScore() {
        return score;
    }

    /**
     * Generates a random integer between a minumum and maximum value
     * @param min Minimum value
     * @param max Maximum value
     * @return random integer between the minimum and maximum
     */
    public static int randint(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }

    /**
     * Inner lane class to generate cars
     */
    private static class Lane extends Obstacle {
        /** car flow direction of the lane (1 is right, -1 is left) */
        private int direction;

        /** speed at which cars travel in the lane */
        private int speed;

        /** is the lane a road or grass */
        private boolean isRoad;

        /** frequency at which cars spawn in the lane */
        private double frequency;

        /**
         * Constructor for either grass or road lanes
         * randomly picks direction and speed of the lane
         * @param x
         * @param y
         * @param width
         * @param height
         */
        public Lane(int x, int y, int width, int height) {
            super(x, y, width, height);
            this.direction = Math.random() >= 0.5 ? -1 : 1;
            this.speed = (int) (Math.random() * 5) + 5;
            this.isRoad = false;
            this.frequency = 0;
        }

        /**
         * Constructor for road lanes
         * sets the spawn frequency of the road
         * @param x
         * @param y
         * @param width
         * @param height
         * @param frequency % chance a car will spawn in a frame
         */
        public Lane(int x, int y, int width, int height, double frequency) {
            this(x, y, width, height);
            this.isRoad = true;
            this.frequency = frequency;
        }

        /**
         * Move the lane based on player movement
         * @param xDistance X distance travelled
         * @param yDistance Y distance travelled
         */
        public void move(int xDistance, int yDistance) {
            moveX(xDistance);
            moveY(yDistance);
        }

        /**
         * @return The frequency at which cars spawn in this lane
         */
        public double getFrequency() {
            return frequency;
        }

        /**
         * Change the frequency at which cars spawn in this lane
         * @param amount The new frequency
         */
        public void setFrequency(double amount) {
            frequency = amount;
        }

        /**
         * Draws the lane
         * @param g Graphics object for drawing
         */
        public void draw(Graphics g) {
            // change colour depending on if its a road or grass
            if (isRoad)
                g.setColor(new Color(168, 168, 168));
            else
                g.setColor(new Color(89, 168, 49));
            g.fillRect(getX(), getY(), getWidth(), getHeight());
        }

        /**
         * Generate new cars in the sample game based on the spawn rates
         * @param frame the current frame of the game
         * @param cars the car arraylist
         */
        public void update(long frame, ArrayList<Car> cars) {
            if (isRoad && frame % (frequency * 25L) == 0) {  // if the lane is a road, and the frequency is reached
                // create and add a car
                Sprite sprite = new Sprite("assets/car.png", 5);
                int x = direction > 0 ? getX() : getX() + getWidth() - sprite.getImage().getWidth();
                cars.add(new Car(x, getY() + getHeight() / 3, direction, speed, sprite));
            }
        }

        /**
         * Generate new cars in the real escape room game based on random number generation
         * @param cars the car arraylist
         */
        public void updateReal(ArrayList<Car> cars) {
            if (isRoad && Math.random() < frequency) {  // if the car is a road and it should spawn
                // create and add a dcar
                Sprite sprite = new Sprite("assets/car.png", 5);
                int x = direction > 0 ? getX() : getX() + getWidth() - sprite.getImage().getWidth();
                cars.add(new Car(x, getY() + getHeight() / 3, direction, speed, sprite));
            }
        }
    }

    /**
     * Inner car class
     */
    private static class Car extends Obstacle {
        /** direction that the car travels in (1 is right, -1 is left */
        private int direction;

        /** speed at which the car travels */
        private int speed;

        /**
         * Constructor for the car, similar as obstacle
         * @param x
         * @param y
         * @param direction Direction which the car travels in
         * @param speed Speed of the car
         * @param sprite Car sprite
         */
        public Car(int x, int y, int direction, int speed, Sprite sprite) {
            super(x, y, sprite);
            this.direction = direction;
            this.speed = speed;
        }

        /**
         * Draw the car moving in the right direction
         * @param g Graphics object for drawing
         */
        public void draw(Graphics g) {
            if (Controller.SHOW_HITBOXES) {  // draw hitboxes of cars if they should be drawn
                g.setColor(Color.WHITE);
                g.drawRect(getX(), getY(), getWidth(), getHeight());
            }

            if (direction == 1)  // draw car normally if it's going right
                g.drawImage(getSprite().getImage(), getX(), getY(), null);
            else  // otherwise flip the sprite and draw it
                g.drawImage(getSprite().getImage(), getX() + getWidth(), getY(), -getWidth(), getHeight(), null);
        }

        /**
         * move the car, and decide if it's out of bounds and should be deleted
         * @param x X position at which it should be deleted
         * @param width Width of the lane
         * @return if the car should be deleted or not
         */
        public boolean move(int x, int width) {
            moveX(speed * direction);

            return getX() < x || getX() + getWidth() > x + width;
        }

    }

    /**
     * Inner sample game class of the walking game that can be implemented as an object within the maze game
     */
    public static class SampleGame {
        /** arraylist to store each lane in the game */
        private ArrayList<Car> cars;

        /** arraylist to store each car in the game */
        private ArrayList<Lane> lanes;

        /** X position of the game */
        private int x;

        /** width of the sample game, how long the lanes are */
        private int width;

        /** frame integer that increments once each frame */
        private long frame;

        /** teacher npc that explains the game and quizzes the player on the topic */
        private Teacher teacher;

        /** if the player is currently in dialogue with the npc */
        private boolean inDialogue;

        /** if the player already went through the dialogue with the npc */
        private boolean readDialogue;

        /** prompt to give the player a message after finishing the game */
        private MessageBox prompt;

        /** List of messages to display in the dialogue, as well as the question with answers */
        public static final MessageBox[] dialogue = new MessageBox[] {
                new MessageBox("Hey there! Welcome to the testing level for walking in the suburbs: aka surviving as a pedestrian."),
                new MessageBox("In this game mode, you must cross the streets without getting hit by a car."),
                new MessageBox("If you do get hit, you \"die\" and are sent back to the beginning."),
                new MessageBox("Before I let you try to walk through the suburbs yourself, I have one quick question for you..."),
                new MessageBox("Which of the following is NOT a reason why walking is unpleasant in the suburbs?",
                        new String[] {
                                "1) Inconsistent sidewalks",
                                "2) Long travel distances",
                                "3) Concrete sidewalks"
                        }, '3'),
                new MessageBox("Correct! The fact that sidewalks are made out of concrete has no significant effect on the walkability of suburbs.", 2),
                new MessageBox("Wrong! Feel free to try again!"),
        };

        /**
         * Constructor for the walking sample game
         * creates a set of 15 lanes with predetermined grass and concrete lanes
         * spawn rates are also pre determined
         * @param x
         * @param y
         * @param width
         * @param height
         */
        public SampleGame(int x, int y, int width, int height) {
            this.lanes = new ArrayList<Lane>();
            this.cars = new ArrayList<Car>();
            this.x = x;
            this.width = width;
            this.teacher = new Teacher(-390, -200, new Sprite("assets/walkingMan.png", 7));
            this.inDialogue = false;

            boolean[] laneTypes = {false, true, false, true, true, false, true, true, false, false, true, true, true, true, false};
            int[] laneFrequencies = {3, 3, 4, 3, 5, 3, 5, 4, 3 };
            int counter = 0;
            for (int i = 0; i < 15; i++) {
                if (laneTypes[i]) {
                    lanes.add(new Lane(x, y - i * height, width, height, laneFrequencies[counter]));
                    counter++;
                } else
                    lanes.add(new Lane(x, y - i * height, width, height));
            }
        }

        /**
         * @return is the user currently in dialogue with the npc
         */
        public boolean isInDialogue() {
            return inDialogue;
        }

        /**
         * Changes whether the player is in dialogue
         * @param inDialogue new boolean representing the player in dialogue
         */
        public void setDialogue(boolean inDialogue) {
            this.inDialogue = inDialogue;
        }

        /**
         * Changes whether the player has gone through the dialogue
         * @param set new boolean representing if the player has read the dialogue
         */
        public void setReadDialogue(boolean set) {
            readDialogue = set;
        }

        /**
         * @return The prompt that should be displayed after the game is played
         */
        public MessageBox getPrompt() {
            return prompt;
        }

        /**
         * clear the prompt to free up screen space
         */
        public void clearPrompt() {
            prompt = null;
        }

        /**
         * move the walking game in relation to player movement
         * @param xDistance X distance travelled
         * @param yDistance Y distance travelled
         */
        public void move(int xDistance, int yDistance) {

            for (Car car : cars) {
                car.moveX(xDistance);
                car.moveY(yDistance);
            }

            for (Lane lane : lanes) {
                lane.move(xDistance, yDistance);
            }
            teacher.moveX(xDistance);
            teacher.moveY(yDistance);

            this.x = lanes.get(0).getX();  // keep track of the X location of the game
        }

        /**
         * Main game loop for the walking sample game
         * @param g Graphics object for drawing
         * @param player Player
         * @return If the map should be reset or not
         */
        public boolean draw(Graphics g, Player player) {
            frame++;
            if (teacher.inRadius(player)) {
                clearPrompt();
                if (!readDialogue)
                    inDialogue = true;
            }

            for (Lane lane : lanes) {
                lane.draw(g);
                lane.update(frame, cars);
            }

            teacher.draw(g);

            ArrayList<Integer> toRemove = new ArrayList<Integer>();
            for (int i = 0; i < cars.size(); i++) {
                cars.get(i).draw(g);
                if (player.collide(cars.get(i))) {  // if the player hits a car
                    prompt = new MessageBox("You got hit by a car! I guess the streets aren't that safe after all...");
                    return true;
                }
                if (cars.get(i).move(x, width))  // move the car, check if it should be deleted
                    toRemove.add(i);
            }

            // funky way to delete cars non-destructively
            for (int i = 0; i < toRemove.size(); i++)
                toRemove.set(i, toRemove.get(i) - i);

            for (int index : toRemove) cars.remove(index);

            // check if win
            if (player.collide(lanes.get(lanes.size() - 1))) {
                prompt = new MessageBox("Congratulations! You survived as a pedestrian!");
                Maze.finishedLevels[0] = true;
                return true;
            }
            return false;
        }
    }
}
