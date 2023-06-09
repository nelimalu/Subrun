import java.awt.*;
import java.util.ArrayList;

public class BikingGame {

    private Lane[] lanes;
    private ArrayList<Vehicle> vehicles;
    private int currentLane;

    public BikingGame() {
        int x = 255;
        int y = -300;
        currentLane = 1;

        vehicles = new ArrayList<Vehicle>();
        lanes = new Lane[] {
                new Lane(x, y, 100, 1000, new Color(70, 70, 70), 0.005),
                new Lane(x + 100, y, 100, 1000, new Color(100, 100, 100), 0.015),
                new Lane(x + 200, y, 100, 1000, new Color(70, 70, 70), 0.005),
        };
    }

    public void paint(Graphics g, Player player) {
        for (Lane lane : lanes) {
            lane.draw(g);
            lane.updateReal(vehicles);
        }

        for (Vehicle vehicle : vehicles) {
            vehicle.draw(g);
        }

        ArrayList<Integer> toRemove = new ArrayList<Integer>();
        for (int i = 0; i < vehicles.size(); i++) {
            vehicles.get(i).draw(g);
            if (player.collideSmall(vehicles.get(i))) {
                System.out.println("DEAD");
                player.resetPosition();

            }
            if (vehicles.get(i).move(800)) // out of bounds
                toRemove.add(i);
        }

        for (int i = 0; i < toRemove.size(); i++)
            toRemove.set(i, toRemove.get(i) - i);

        for (int index : toRemove) vehicles.remove(index);
    }

    public int getCurrentLane() {
        return currentLane;
    }

    public void setCurrentLane(int amount) {
        currentLane = amount;
    }

    private static class Lane extends Obstacle {
        private Color colour;
        private double rate;
        public Lane(int x, int y, int width, int height, Color colour, double rate) {
            super(x, y, width, height);
            this.colour = colour;
            this.rate = rate;
        }

        public void move(int xDistance, int yDistance) {
            moveX(xDistance);
            moveY(yDistance);
        }

        public void draw(Graphics g) {
            g.setColor(colour);
            g.fillRect(getX(), getY(), getWidth(), getHeight());
        }

        public void update(ArrayList<Vehicle> vehicles, boolean playing) {
            if (Math.random() < rate && playing) {
                Sprite sprite = new Sprite("assets/frontFacingCar.png", 3);
                int speed = (int) (Math.random() * 5) + 5;
                vehicles.add(new Vehicle(getX() + 5, getY(), speed, sprite));
            }
        }

        public void updateReal(ArrayList<Vehicle> vehicles) {
            if (Math.random() < rate) {
                Sprite sprite = new Sprite("assets/frontFacingCar.png", 3);
                int speed = (int) (Math.random() * 5) + 5;
                vehicles.add(new Vehicle(getX() + 5, getY(), speed, sprite));
            }
            rate += 0.0001;
        }
    }


    private static class Vehicle extends Obstacle {
        private int speed;
        public Vehicle(int x, int y, int speed, Sprite sprite) {
            super(x, y, sprite);
            this.speed = speed;
        }

        public boolean move(int y) {
            moveY(speed);

            return getY() > y;
        }
    }

    public static class SampleGame {
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
