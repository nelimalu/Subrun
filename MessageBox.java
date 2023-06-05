import java.awt.*;
public class MessageBox {

    private String text;
    private static final int X = 5;
    private static final int Y = 300;
    private static final int WIDTH = 775;
    private static final int HEIGHT = 160;
    private static final int STROKE = 10;
    private static final Font font = new Font("Helvetica Neue", Font.BOLD, 20);
    private static final Font smallFont = new Font("Helvetica Neue", Font.BOLD, 10);

    public MessageBox(String text) {
        this.text = text;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(X, Y, WIDTH, HEIGHT);

        g.setColor(Color.BLACK);
        g.fillRect(X + STROKE, Y + STROKE, WIDTH - STROKE * 2, HEIGHT - STROKE * 2);

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(text, X + 20, Y + 40);

        g.setFont(smallFont);
        g.drawString("click anywhere to continue...", X + 325, Y + 140);


    }

}
