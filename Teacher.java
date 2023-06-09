import java.awt.*;

/**
 * Teacher class represents the teacher npc in game that the
 * user can interact with.
 * <strong>Course info:</strong>
 * ICS4U0 with V. Krasteva
 *
 * @version 1.0
 * @author [50%] Luka Jovanovic & [50%] Brian Song
 * Created on 2023/06/07
 */
public class Teacher extends Obstacle {
    /** The message box displayed by the teacher */
    private MessageBox message;

    /**
     * Teacher constructor
     * Constructs a Teacher object with the specified position and sprite
     *
     * @param x teacher position x coordinate
     * @param y teacher position y coordinate
     * @param sprite teacher's sprite
     */
    public Teacher(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    /**
     * Teacher constructor
     * Constructs a Teacher object with the specified position, sprite, and message
     *
     * @param x teacher position x coordinate
     * @param y teacher position y coordinate
     * @param sprite teacher's sprite
     * @param message array of strings containing the messages teacher says in order
     */
    public Teacher(int x, int y, Sprite sprite, String[] message) {
        super(x, y, sprite);
        this.message = new MessageBox(message);
    }

    /**
     * Teacher constructor
     * Constructs a Teacher object with the specified position
     *
     * @param x teacher position x coordinate
     * @param y teacher position y coordinate
     */
    public Teacher(int x, int y) {
        super(x, y, 1, 1);
    }

    /**
     * inRadius method
     * Checks if the player is within the radius of the teacher
     *
     * @param player the player object to check
     * @return true if the player is within the teacher's radius, false otherwise
     */
    public boolean inRadius(Player player) {
        return Math.hypot(player.getX() - getX(), player.getY() - getY()) < 120;
    }

    /**
     * Getter method for teacher message
     * @return message
     */
    public MessageBox getMessage() {
        return message;
    }

    /**
     * Paints prompt if player is within teacher radius
     * @param g graphics object to be painted on
     * @param player target player to be checked
     */
    public void paintPrompt(Graphics g, Player player) {
        if (inRadius(player)) {
            message.draw(g);
        }
    }
}