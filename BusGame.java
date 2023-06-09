import java.awt.*;

public class BusGame {

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

    private static final Font LARGE_FONT = new Font("Monospaced", Font.BOLD, 40);
    private static final Font MEDIUM_FONT = new Font("Monospaced", Font.BOLD, 20);
    private static final Font SMALL_FONT = new Font("Monospaced", Font.BOLD, 10);

    private Bus bus;
    private Obstacle house;
    private int stopIndex;
    private String destination;
    private int destinationIndex;
    private long frame;
    private int waitTime;

    public BusGame() {
        bus = new Bus(120, -200, new Sprite("assets/bus.png", 15));
        house = new Obstacle(100, 0, new Sprite("assets/blueHouse.png"));
        destinationIndex = 5; //randint(10, 50);
        destination = BUS_STOPS[destinationIndex];
        frame = 0;
        waitTime = (int) (Math.random() * 25) + 25;
    }

    public static int randint(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }

    public void checkWin(EscapeRoom room) {
        if (BUS_STOPS[stopIndex].equals(destination))
            room.setGame("ALIVE");
        else
            room.setGame("BUSLOSS");
    }

    public void paint(Graphics g, EscapeRoom room) {
        int x = 0;
        int y = 0;
        int width = 1000;
        int height = 800;

        frame++;
        if (frame % waitTime == 0) {
            stopIndex++;
            waitTime = (int) (Math.random() * 25) + 30;
        }

        if (stopIndex > destinationIndex) {
            room.setGame("BUSLOSS");
        }

        house.moveX(-15);
        if (house.getX() < -300)
            house.moveX(1200);

        g.setColor(new Color(168, 168, 168));
        g.fillRect(x, y, width, height);

        g.setColor(new Color(89, 168, 49));
        g.fillRect(x, y, width, height / 4);
        g.fillRect(x, (int) (y + height * 0.75), width, height / 4);

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

        house.draw(g);
        bus.draw(g);

    }

    private static class Bus extends Obstacle {

        public Bus(int x, int y, Sprite sprite) {
            super(x, y, sprite);

        }
    }

    public static class SampleGame {
        private int x;
        private int y;
        private int width;
        private int height;
        private Teacher teacher;
        private Teacher enterRadius;
        private boolean inDialogue;
        private boolean readDialogue;
        private boolean playing;
        private Bus bus;
        private String destination;
        private int stopIndex;
        private long frame;
        private int waitTime;
        private MessageBox prompt;
        private Obstacle house;
        private static int END_INDEX = 10;

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

        public boolean isInDialogue() {
            return inDialogue;
        }

        public void setDialogue(boolean inDialogue) {
            this.inDialogue = inDialogue;
        }

        public void setReadDialogue(boolean set) {
            readDialogue = set;
        }

        public boolean isAtStop() {
            return destination.equals(BUS_STOPS[stopIndex]);
        }

        public boolean isPlaying() {
            return playing;
        }

        public MessageBox getPrompt() {
            return prompt;
        }

        public void handleWin() {
            playing = false;
            prompt = new MessageBox("Congratulations! You have successfully gotten off at the right stop.");
        }

        public void handleLoss() {
            playing = false;
            stopIndex = 0;
            prompt = new MessageBox("Oh no! You missed your stop!");
        }

        public void clearPrompt() {
            prompt = null;
        }

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

        public void draw(Graphics g, Player player, int xOffset, int yOffset, Maze maze) {

            if (playing) {
                frame++;
                if (frame % waitTime == 0) {
                    stopIndex++;
                    waitTime = (int) (Math.random() * 25) + 100;
                }

                if (stopIndex > END_INDEX) {
                    maze.resetPositions();
                    handleLoss();
                }

                house.moveX(-15);
                if (house.getX() < -300)
                    house.moveX(1200);


            }

            if (teacher.inRadius(player)) {
                clearPrompt();
                maze.clearPrompt();
                if (!readDialogue)
                    inDialogue = true;
            }

            if (!playing && enterRadius.inRadius(player)) {

                maze.move(-xOffset, -yOffset + 2820);
                playing = true;
            }



            g.setColor(new Color(168, 168, 168));
            g.fillRect(x, y, width, height);

            g.setColor(new Color(89, 168, 49));
            g.fillRect(x, y, width, height / 4);
            g.fillRect(x, (int) (y + height * 0.75), width, height / 4);

            teacher.draw(g);
            bus.draw(g);
            house.draw(g);

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
