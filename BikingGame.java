import java.awt.*;
import java.util.ArrayList;

public class BikingGame {

    private int x;
    private int y;
    private int width;
    private int height;
    private Lane[] lanes;
    private ArrayList<Vehicle> vehicles;

    private static class Lane extends Obstacle {
        private Color colour;
        public Lane(int x, int y, int width, int height, Color colour) {
            super(x, y, width, height);
            this.colour = colour;
        }

        public void move(int xDistance, int yDistance) {
            moveX(xDistance);
            moveY(yDistance);
        }

        public void draw(Graphics g) {
            g.setColor(colour);
            g.fillRect(getX(), getY(), getWidth(), getHeight());
        }

        public void update(ArrayList<Vehicle> vehicles) {
            if (Math.random() < 0.02) {
                Sprite sprite = new Sprite("assets/frontFacingCar.png", 3);
                int speed = (int) (Math.random() * 5) + 5;
                vehicles.add(new Vehicle(getX(), getY(), speed, sprite));
            }
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
        private boolean readDialogue;
        private boolean inDialogue;
        private boolean playing;

        public static final MessageBox[] dialogue = new MessageBox[] {
                new MessageBox("Howdy! Welcome to the testing level for biking in the suburbs: aka surviving as a cyclist."),
                new MessageBox("In this game mode, you must use left and right arrow keys to bike on a car filled road and make it to your destination."),
                new MessageBox("If you do get hit, you \"die\" and are sent back to the beginning."),
                new MessageBox("Before I let you try to bike through the suburbs yourself, I have one quick question for you..."),
                new MessageBox("In which location is it illegal to bike?",
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
                    new Lane(x, y, 100, 2000, new Color(70, 70, 70)),
                    new Lane(x + 100, y, 100, 2000, new Color(100, 100, 100)),
                    new Lane(x + 200, y, 100, 2000, new Color(70, 70, 70)),
            };
            this.y = y + height;
            readDialogue = false;
            inDialogue = false;
            playing = false;
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

        public void draw(Graphics g, Player player, int xOffset, int yOffset, Maze maze) {
            if (teacher.inRadius(player)) {
                if (!readDialogue)
                    inDialogue = true;
            }

            System.out.println(playing);

            if (!playing && enterRadius.inRadius(player)) {
                playing = true;
                maze.move(-300 - xOffset, 0);
            }

            if (playing) {
                if (yOffset < 600)
                    maze.move(0, 5);
            }

            for (Lane lane : lanes) {
                lane.draw(g);
                lane.update(vehicles);
            }

            ArrayList<Integer> toRemove = new ArrayList<Integer>();
            for (int i = 0; i < vehicles.size(); i++) {
                vehicles.get(i).draw(g);
                if (vehicles.get(i).move(y)) // out of bounds
                    toRemove.add(i);
            }

            for (int i = 0; i < toRemove.size(); i++)
                toRemove.set(i, toRemove.get(i) - i);

            for (int index : toRemove) vehicles.remove(index);

            teacher.draw(g);

        }
    }

}
