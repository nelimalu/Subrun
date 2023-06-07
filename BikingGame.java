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
    }

    private static class Vehicle extends Obstacle {

        public Vehicle(int x, int y, Sprite sprite) {
            super(x, y, sprite);
        }
    }

    public static class SampleGame {
        private long frame;
        private Lane[] lanes;
        private ArrayList<Vehicle> vehicles;
        private Teacher teacher;
        public SampleGame(int x, int y, int width, int height) {
            lanes = new Lane[] {
                    new Lane(x, y, 100, 2000, new Color(70, 70, 70)),
                    new Lane(x + 100, y, 100, 2000, new Color(100, 100, 100)),
                    new Lane(x + 200, y, 100, 2000, new Color(70, 70, 70)),
            };
            frame = 0;
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

            //this.x = lanes.get(0).getX();
        }

        public void draw(Graphics g) {
            frame++;

            for (Lane lane : lanes) {
                lane.draw(g);
            }

            teacher.draw(g);

        }
    }

}
