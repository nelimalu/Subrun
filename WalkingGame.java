import java.util.ArrayList;
import java.awt.*;

public class WalkingGame {

    private ArrayList<Lane> lanes;
    private ArrayList<Car> cars;

    private static class Lane {
        private int x;
        private int y;
        private int width;
        private int height;
        private int direction;
        private int speed;
        private boolean isRoad;
        private int frequency;

        public Lane(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.direction = Math.random() >= 0.5 ? -1 : 1;
            this.speed = (int) (Math.random() * 5) + 5;
            this.isRoad = false;
            this.frequency = 0;
        }

        public Lane(int x, int y, int width, int height, int frequency) {
            this(x, y, width, height);
            this.isRoad = true;
            this.frequency = frequency;
        }

        public void move(int xDistance, int yDistance) {
            x += xDistance;
            y += yDistance;
        }

        public void draw(Graphics g) {

            if (isRoad)
                g.setColor(new Color(168, 168, 168));
            else
                g.setColor(new Color(89, 168, 49));
            g.fillRect(x, y, width, height);
        }

        public void update(long frame, ArrayList<Car> cars) {
            if (isRoad && frame % (frequency * 25L) == 0) {
                cars.add(new Car(x, y, direction, speed, new Sprite("assets/car.png", 3)));
            }
        }
    }

    private static class Car extends Obstacle {
        private int direction;
        private int speed;

        public Car(int x, int y, int direction, int speed, Sprite sprite) {
            super(x, y, sprite);
            this.direction = direction;
            this.speed = speed;
        }

        public void draw(Graphics g) {
            /*
            if (direction == 1)
                g.drawImage(getSprite().getImage(), getX(), getY(), null);
            else
                g.drawImage(getSprite().getImage(), getX() + getWidth(), getY(), -getWidth(), getHeight(), null);

             */
        }

        public void move() {
            moveX(speed * direction);
        }

    }

    public static class SampleGame {

        private ArrayList<Car> cars;
        private ArrayList<Lane> lanes;
        private long frame;

        public SampleGame(int x, int y, int width, int height) {
            lanes = new ArrayList<Lane>();
            cars = new ArrayList<Car>();

            boolean[] laneTypes = {false, true, false, true, true, false, true, true, false, false, true, true, true, true, false};
            int[] laneFrequencies = {3, 2, 4, 3, 5, 2, 5, 4, 2};  // 9
            int counter = 0;
            for (int i = 0; i < 15; i++) {
                if (laneTypes[i]) {
                    lanes.add(new Lane(x, y - i * height, width, height, laneFrequencies[counter]));
                    counter++;
                } else
                    lanes.add(new Lane(x, y - i * height, width, height));
            }
        }

        public void move(int xDistance, int yDistance) {
            for (Car car : cars) {
                car.moveX(xDistance);
                car.moveY(yDistance);
            }

            for (Lane lane : lanes) {
                lane.move(xDistance, yDistance);
            }
        }

        public void draw(Graphics g) {
            frame++;

            for (Lane lane : lanes) {
                lane.draw(g);
                lane.update(frame, cars);
            }

            for (Car car : cars) {
                car.draw(g);
                car.move();
            }


        }
    }
}
