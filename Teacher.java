import java.awt.*;

public class Teacher extends Obstacle {
    private MessageBox message;

    public Teacher(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }
    public Teacher(int x, int y, Sprite sprite, String[] message) {
        super(x, y, sprite);
        this.message = new MessageBox(message);
    }
    public Teacher(int x, int y) { super(x, y, 1, 1); }

    public boolean inRadius(Player player) {
        return Math.hypot(player.getX() - getX(), player.getY() - getY()) < 120;
    }

    public MessageBox getMessage() {
        return message;
    }

    public void paintPrompt(Graphics g, Player player) {
        if (inRadius(player)) {
            message.draw(g);
        }
    }
}
