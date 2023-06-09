import java.awt.*;

/**
 * BusGame class handles and draws the bus game, both in the escape room and maze room
 * Inner classes are used for handling repetitiveness within the class
 *
 * <strong>Course info:</strong>
 * ICS4U0 with V. Krasteva
 *
 * @version 1.0
 * @author Luka Jovanovic & Brian Song
 * Created on 2023/07/04
 */
public class BusGame {

    /** array of common 100 street names */
    private static final String[] BUS_STOPS = {
            "Main Street",
            "Church Street",
            "Main Street North",
            "Main Street South",
            "High Street",
            "Elm Street",
            "Main Street West",
            "Washington Street",
            "Park Avenue",
            "Main Street East",
            "Walnut Street",
            "2nd Street",
            "Chestnut Street",
            "Maple Avenue",
            "Maple Street",
            "Broad Street",
            "Oak Street",
            "Center Street",
            "River Road",
            "Pine Street",
            "Water Street",
            "Market Street",
            "South Street",
            "Union Street",
            "Park Street",
            "3rd Street",
            "Washington Avenue",
            "Cherry Street",
            "Highland Avenue",
            "Court Street",
            "North Street",
            "Mill Street",
            "4th Street",
            "Spring Street",
            "School Street",
            "Prospect Street",
            "Franklin Street",
            "Central Avenue",
            "1st Street",
            "State Street",
            "West Street",
            "Front Street",
            "Jefferson Street",
            "Cedar Street",
            "Park Place",
            "Locust Street",
            "Bridge Street",
            "Madison Avenue",
            "Jackson Street",
            "Spruce Street",
            "Ridge Road",
            "Meadow Lane",
            "Grove Street",
            "5th Street",
            "Pearl Street",
            "Lincoln Street",
            "Pleasant Street",
            "Pennsylvania Avenue",
            "Dogwood Drive",
            "Madison Street",
            "Lincoln Avenue",
            "Jefferson Avenue",
            "Adams Street",
            "Academy Street",
            "7th Street",
            "4th Street West",
            "Route 1",
            "Green Street",
            "East Street",
            "3rd Street West",
            "11th Street",
            "Summit Avenue",
            "River Street",
            "Elizabeth Street",
            "Cherry Lane",
            "9th Street",
            "6th Street",
            "2nd Avenue",
            "12th Street",
            "Virginia Avenue",
            "Hill Street",
            "Hickory Lane",
            "Charles Street",
            "5th Avenue",
            "10th Street",
            "Liberty Street",
            "Fairway Drive",
            "Woodland Drive",
            "Winding Way",
            "Vine Street",
            "Route 30",
            "Monroe Street",
            "Delaware Avenue",
            "Colonial Drive",
            "Church Road",
            "Broadway",
            "1st Avenue",
            "Valley Road",
            "Sunset Drive",
            "Prospect Avenue",
    };

    /** Large font used to display the current stop */
    private static final Font LARGE_FONT = new Font("Monospaced", Font.BOLD, 40);

    /** Medium font used to display the next stop and destination */
    private static final Font MEDIUM_FONT = new Font("Monospaced", Font.BOLD, 20);

    /** small font */
    private static final Font SMALL_FONT = new Font("Monospaced", Font.BOLD, 10);

    /** bus object */
    private Bus bus;

    /** house that moves in the background to simulate movement*/
    private Obstacle house;

    /** the index of the street the bus is currently on in relation to the street names array */
    private int stopIndex;

    /** street name of the destination */
    private String destination;

    /** index of the destination in the array of street names */
    private int destinationIndex;

    /** integer that increments once each frame*/
    private long frame;

    /** amount of time that should be waited before switching to the next stop */
    private int waitTime;

    /**
     * Bus game constructor that randomly picks the destination
     */
    public BusGame() {
        bus = new Bus(120, -200, new Sprite("assets/bus.png", 15));
        house = new Obstacle(100, 0, new Sprite("assets/blueHouse.png"));
        destinationIndex = randint(10, 50);
        destination = BUS_STOPS[destinationIndex];
        frame = 0;
        waitTime = (int) (Math.random() * 25) + 25;
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
     * When the player clicks to get off the bus, check if the player got off at the right time or not
     * @param room EscapeRoom object
     */
    public void checkWin(EscapeRoom room) {
        if (BUS_STOPS[stopIndex].equals(destination)) {  // if it's the right destination
            room.setGame("ALIVE");
            room.incrementBusHighScore();
            room.busPerson.getMessage().setTextArray("You have successfully completed this task! (win one game)", 1);
        }
        else
            room.setGame("BUSLOSS");  // switch to custom bus loss screen
    }

    /**
     * Main game loop for the bus game in the escape room
     * @param g Graphics object for drawing
     * @param room EscapeRoom object
     */
    public void paint(Graphics g, EscapeRoom room) {
        // position of the game and background
        int x = 0;
        int y = 0;
        int width = 1000;
        int height = 800;

        frame++;
        if (frame % waitTime == 0) {  // increase stop index based on movement
            stopIndex++;
            waitTime = (int) (Math.random() * 25) + 30;
        }

        if (stopIndex > destinationIndex) {  // check if the player missed their stop
            room.setGame("BUSLOSS");
        }

        house.moveX(-15);  // move the house in the background to simulate movement
        if (house.getX() < -300)  // if its off the screen loop it back in front
            house.moveX(1200);

        // create box at the bottom with a border
        g.setColor(new Color(168, 168, 168));
        g.fillRect(x, y, width, height);

        g.setColor(new Color(89, 168, 49));
        g.fillRect(x, y, width, height / 4);
        g.fillRect(x, (int) (y + height * 0.75), width, height / 4);

        g.setColor(new Color(159, 159, 159));
        g.fillRect(0, 300, 785, 200);

        g.setColor(new Color(37, 37, 37));
        g.fillRect(10, 310, 765, 140);

        // write relevant information on box
        g.setColor(Color.WHITE);
        g.setFont(MEDIUM_FONT);
        g.drawString("NEXT STOP: " + BUS_STOPS[stopIndex + 1], 20, 360);
        g.setFont(SMALL_FONT);
        g.drawString("CURRENT STOP:", 20, 400);
        g.setFont(LARGE_FONT);
        g.drawString(BUS_STOPS[stopIndex], 20, 430);

        g.setFont(MEDIUM_FONT);
        g.setColor(Color.RED);
        g.drawString("DESTINATION: " + destination, 20, 340);

        house.draw(g);  // draw the background house
        bus.draw(g); // draw the bus

    }

    /**
     * Bus class that inherits obstacle, no extra functionality, but is here in case we expand in the future
     */
    private static class Bus extends Obstacle {

        /**
         * @param x
         * @param y
         * @param sprite
         */
        public Bus(int x, int y, Sprite sprite) {
            super(x, y, sprite);

        }
    }

    /**
     * Inner sample game class for the bus game that can be implemented in the maze game
     */
    public static class SampleGame {
        /** X position of the sample game */
        private int x;

        /** Y position of the sample game */
        private int y;

        /** width of the sample game */
        private int width;

        /** height of the sample game */
        private int height;

        /** teacher NPC that explains how to play the game and quizzes the player */
        private Teacher teacher;

        /** Teacher object that is used to detect if the play gets close to the bus to enter the game */
        private Teacher enterRadius;

        /** if the player is currently in dialogue with the npc */
        private boolean inDialogue;

        /** if the player has read the dialogue */
        private boolean readDialogue;

        /** if the player is currently in the bus game */
        private boolean playing;

        /** bus object */
        private Bus bus;

        /** name of the destination street */
        private String destination;

        /** index of the stop that the bus is currently at */
        private int stopIndex;

        /** number that increments once each frame */
        private long frame;

        /** amount of time to wait before switching to the next bus stop */
        private int waitTime;

        /** prompt to be displayed when the player finished the sample game */
        private MessageBox prompt;

        /** house that moves in the background to simulate movement */
        private Obstacle house;

        /** index of the bus stop to dismount at in relation to the bus stops static array */
        private static int END_INDEX = 10;

        /** List of messages to display in the dialogue, as well as the question with answers */
        public static final MessageBox[] dialogue = new MessageBox[] {
                new MessageBox("Hey there! Welcome to the testing level for taking the bus in the suburbs."),
                new MessageBox("In this game mode, you must time getting off the bus to make sure you don't accidentally miss your stop. (Walk towards the bus to board)"),
                new MessageBox("Your destination will be displayed on the screen, along with the current stop and next stop. Click anywhere when you have arrived at your stop to exit the bus."),
                new MessageBox("Before I let you try to walk through the suburbs yourself, I have one quick question for you..."),
                new MessageBox("Which of the following is a reason for low bus funding in suburbs?",
                        new String[] {
                                "1) Low ridership",
                                "2) Too many lanes",
                                "3) Low frequency"
                        }, '1'),
                new MessageBox("Correct! Low bus ridership in the suburbs leads to budget cuts, spiraling into even less ridership.", 2),
                new MessageBox("Wrong! Feel free to try again!"),
        };

        /**
         * Sample game constructor for the bus game that creates all graphics objects
         * @param x
         * @param y
         * @param width
         * @param height
         */
        public SampleGame(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;

            inDialogue = false;
            readDialogue = false;
            playing = false;
            stopIndex = 0;
            waitTime = (int) (Math.random() * 25) + 100;
            destination = BUS_STOPS[END_INDEX];
            teacher = new Teacher(350, -2400, new Sprite("assets/BusMan.png", 7));
            enterRadius = new Teacher(350, -2600);
            bus = new Bus(120, -3000, new Sprite("assets/bus.png", 15));
            house = new Obstacle(0, -2900, new Sprite("assets/blueHouse.png"));
        }

        /**
         * @return If the player is currently in dialogue
         */
        public boolean isInDialogue() {
            return inDialogue;
        }

        /**
         * changes if the player is currently in dialogue
         * @param inDialogue If the player should be in dialogue or not
         */
        public void setDialogue(boolean inDialogue) {
            this.inDialogue = inDialogue;
        }

        /**
         * Changes if the player read the dialogue
         * @param set If the player should have read the dialogue or not
         */
        public void setReadDialogue(boolean set) {
            readDialogue = set;
        }

        /**
         * @return If the player is currently at his destination or not
         */
        public boolean isAtStop() {
            return destination.equals(BUS_STOPS[stopIndex]);
        }

        /**
         * @return if the player is currently playing the bus game
         */
        public boolean isPlaying() {
            return playing;
        }

        /**
         * @return The prompt to be displayed after the game
         */
        public MessageBox getPrompt() {
            return prompt;
        }

        /**
         * end the game and creates a prompt to be shown after
         */
        public void handleWin() {
            playing = false;
            prompt = new MessageBox("Congratulations! You have successfully gotten off at the right stop.");
        }

        /**
         * end the game and creates a prompt to be shown after
         */
        public void handleLoss() {
            playing = false;
            stopIndex = 0;
            prompt = new MessageBox("Oh no! You missed your stop!");
        }

        /** reset the prompt */
        public void clearPrompt() {
            prompt = null;
        }

        /**
         * move the bus sample game in relation oo player movement
         * @param xDistance X distance travelled
         * @param yDistance Y distance travelled
         */
        public void move(int xDistance, int yDistance) {
            x += xDistance;
            y += yDistance;

            teacher.moveX(xDistance);
            teacher.moveY(yDistance);
            enterRadius.moveX(xDistance);
            enterRadius.moveY(yDistance);
            house.moveX(xDistance);
            house.moveY(yDistance);
            bus.moveX(xDistance);
            bus.moveY(yDistance);
        }

        /**
         * Main game loop for the bus sample game
         * @param g Graphics object for drawing
         * @param player Player object
         * @param xOffset X distance travelled in relation to the origin
         * @param yOffset Y distance travelled in relation to the origin
         * @param maze Maze object
         */
        public void draw(Graphics g, Player player, int xOffset, int yOffset, Maze maze) {

            if (playing) {
                frame++;
                if (frame % waitTime == 0) {  // if enough time has passed, go to the next stop
                    stopIndex++;
                    waitTime = (int) (Math.random() * 25) + 100;
                }

                if (stopIndex > END_INDEX) {  // if player missed stop, they lose
                    maze.resetPositions();
                    handleLoss();
                }

                // loop the house to simulate movement
                house.moveX(-15);
                if (house.getX() < -300)
                    house.moveX(1200);
            }

            // if the player is near the teacher and hasn't heard the dialogue, give the dialogue
            if (teacher.inRadius(player)) {
                clearPrompt();
                maze.clearPrompt();
                if (!readDialogue)
                    inDialogue = true;
            }

            // begin the game if the player steps close to the bus
            if (!playing && enterRadius.inRadius(player)) {
                maze.move(-xOffset, -yOffset + 2820);
                playing = true;
            }

            // draw ground and road
            g.setColor(new Color(168, 168, 168));
            g.fillRect(x, y, width, height);

            g.setColor(new Color(89, 168, 49));
            g.fillRect(x, y, width, height / 4);
            g.fillRect(x, (int) (y + height * 0.75), width, height / 4);

            teacher.draw(g);
            bus.draw(g);
            house.draw(g);

            // draw info box with current stop, next stop, and destination
            if (playing) {
                g.setColor(new Color(159, 159, 159));
                g.fillRect(0, 300, 785, 200);

                g.setColor(new Color(37, 37, 37));
                g.fillRect(10, 310, 765, 140);

                g.setColor(Color.WHITE);
                g.setFont(MEDIUM_FONT);
                g.drawString("NEXT STOP: " + BUS_STOPS[stopIndex + 1], 20, 360);
                g.setFont(SMALL_FONT);
                g.drawString("CURRENT STOP:", 20, 400);
                g.setFont(LARGE_FONT);
                g.drawString(BUS_STOPS[stopIndex], 20, 430);

                g.setFont(MEDIUM_FONT);
                g.setColor(Color.RED);
                g.drawString("DESTINATION: " + destination, 20, 340);
            }
        }
    }


}
