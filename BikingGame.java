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
        private int y;
        public SampleGame(int x, int y, int width, int height) {
            lanes = new Lane[] {
                    new Lane(x, y, 100, 2000, new Color(70, 70, 70)),
                    new Lane(x + 100, y, 100, 2000, new Color(100, 100, 100)),
                    new Lane(x + 200, y, 100, 2000, new Color(70, 70, 70)),
            };
            this.y = y + height;
            vehicles = new ArrayList<Vehicle>();
            teacher = new Teacher(655, -80, new Sprite("assets/bikingMan.png", 6));
        }

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

            y += yDistance;
        }

        public void draw(Graphics g) {

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
