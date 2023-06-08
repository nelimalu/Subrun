public class Teacher extends Obstacle {

    public Teacher(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }
    public Teacher(int x, int y) { super(x, y, 1, 1); }

    public boolean inRadius(Player player) {
        return Math.hypot(player.getX() - getX(), player.getY() - getY()) < 120;
    }

}
