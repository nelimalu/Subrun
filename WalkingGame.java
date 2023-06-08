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
                Sprite sprite = new Sprite("assets/car.png", 5);
                int x = direction > 0 ? getX() : getX() + getWidth() - sprite.getImage().getWidth();
                cars.add(new Car(x, getY() + getHeight() / 3, direction, speed, sprite));
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
            if (Controller.SHOW_HITBOXES) {
                g.setColor(Color.WHITE);
                g.drawRect(getX(), getY(), getWidth(), getHeight());
            }

            if (direction == 1)
                g.drawImage(getSprite().getImage(), getX(), getY(), null);
            else
                g.drawImage(getSprite().getImage(), getX() + getWidth(), getY(), -getWidth(), getHeight(), null);


        }

        public boolean move(int x, int width) {
            moveX(speed * direction);

            return getX() < x || getX() + getWidth() > x + width;
        }

    }

    public static class SampleGame {

        private ArrayList<Car> cars;
        private ArrayList<Lane> lanes;
        private int x;
        private int xOffset;
        private int yOffset;
        private int width;
        private long frame;
        private Teacher teacher;
        private boolean inDialogue;
        private boolean readDialogue;
        private MessageBox prompt;
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
                new MessageBox("Wrong! The fact that sidewalks are made out of concrete has no significant effect on the walkability of suburbs."),
        };

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

        public boolean isInDialogue() {
            return inDialogue;
        }

        public void setDialogue(boolean inDialogue) {
            this.inDialogue = inDialogue;
        }

        public void setReadDialogue(boolean set) {
            readDialogue = set;
        }

        public MessageBox getPrompt() {
            return prompt;
        }

        public void clearPrompt() {
            prompt = null;
        }

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

            this.x = lanes.get(0).getX();
        }

        /**
         * @param g Graphics
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
                if (player.collide(cars.get(i))) {
                    prompt = new MessageBox("You got hit by a car! I guess the streets aren't that safe after all...");
                    return true;
                }
                if (cars.get(i).move(x, width))
                    toRemove.add(i);
            }

            for (int i = 0; i < toRemove.size(); i++)
                toRemove.set(i, toRemove.get(i) - i);

            for (int index : toRemove) cars.remove(index);

            // check if win
            if (player.collide(lanes.get(lanes.size() - 1))) {
                prompt = new MessageBox("Congratulations! You survived as a pedestrian!");
                return true;
            }
            return false;
        }
    }
}
