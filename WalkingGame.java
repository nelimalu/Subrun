import java.sql.SQLOutput;
import java.util.ArrayList;
import java.awt.*;

public class WalkingGame {

    private ArrayList<Lane> lanes;
    private ArrayList<Car> cars;

    private static class Lane extends Obstacle {
        private int direction;
        private int speed;
        private boolean isRoad;
        private int frequency;

        public Lane(int x, int y, int width, int height) {
            super(x, y, width, height);
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
            moveX(xDistance);
            moveY(yDistance);
        }

        public void draw(Graphics g) {

            if (isRoad)
                g.setColor(new Color(168, 168, 168));
            else
                g.setColor(new Color(89, 168, 49));
            g.fillRect(getX(), getY(), getWidth(), getHeight());
        }

        public void update(long frame, ArrayList<Car> cars) {
            if (isRoad && frame % (frequency * 25L) == 0) {
                int x = direction > 0 ? getX() : getX() + getWidth();
                cars.add(new Car(x, getY() - getHeight() / 3, direction, speed, new Sprite("assets/car.png", 5)));
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

            if (direction == 1)
                g.drawImage(getSprite().getImage(), getX(), getY(), null);
            else
                g.drawImage(getSprite().getImage(), getX() + getWidth(), getY(), -getWidth(), getHeight(), null);


        }

        public boolean move(int x, int width) {
            moveX(speed * direction);

            return getX() < x || getX() > x + width;
        }

    }

    public static class SampleGame {

        private ArrayList<Car> cars;
        private ArrayList<Lane> lanes;
        private int x;
        private int width;
        private long frame;

        public SampleGame(int x, int y, int width, int height) {
            lanes = new ArrayList<Lane>();
            cars = new ArrayList<Car>();
            this.x = x;
            this.width = width;

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
            this.x = lanes.get(0).getX();
        }

        public void draw(Graphics g, Player player) {
            frame++;

            for (Lane lane : lanes) {
                lane.draw(g);
                lane.update(frame, cars);
            }

            ArrayList<Integer> toRemove = new ArrayList<Integer>();
            for (int i = 0; i < cars.size(); i++) {
                cars.get(i).draw(g);
                if (player.collide(cars.get(i))) {
                    System.out.println("DEAD");
                }
                if (cars.get(i).move(x, width))
                    toRemove.add(i);
            }

            for (int i = 0; i < toRemove.size(); i++)
                toRemove.set(i, toRemove.get(i) - i);

            for (int index : toRemove) cars.remove(index);

            // check if win
            if (player.collide(lanes.get(lanes.size() - 1))) {
                System.out.println("WINNERIASJDF");
            }
        }
    }
}
