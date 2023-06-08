import java.awt.*;
public class MessageBox {

    private String text;
    private String[] textArray;
    private String[] answers;
    private boolean question;
    private char answer;
    private int change;
    private boolean displayClick;
    private static final int X = 5;
    private static final int Y = 300;
    private static final int WIDTH = 775;
    private static final int HEIGHT = 160;
    private static final int STROKE = 10;
    private static final int MAX_CHARS = 60;
    private static final Font font = new Font("Monospaced", Font.BOLD, 20);
    private static final Font smallFont = new Font("Monospaced", Font.BOLD, 10);

    public MessageBox(String text) {
        this.text = text;
        this.answers = new String[0];
        this.question = false;
        this.change = 1;
        this.displayClick = true;

        this.textArray = new String[text.length() / MAX_CHARS + 1];
        for (int i = 0; i < text.length() / MAX_CHARS + 1; i++) {
            try {
                textArray[i] = text.substring(i * MAX_CHARS, (i + 1) * MAX_CHARS);
                textArray[i] += "-";
            } catch (StringIndexOutOfBoundsException e) {
                textArray[i] = text.substring(i * MAX_CHARS);
            }
        }

    }

    public MessageBox(String[] text) {
        this.answers = new String[0];
        this.question = false;
        this.change = 1;
        this.textArray = text;
        this.displayClick = false;
    }

    public MessageBox(String text, String[] answers, char answer) {
        this.text = text;
        this.answers = answers;
        this.question = true;
        this.answer = answer;
        this.change = 1;
        this.displayClick = true;

        this.textArray = new String[(text.length() / (MAX_CHARS / 2))+ 1];
        for (int i = 0; i < textArray.length; i++) {
            try {
                textArray[i] = text.substring(i * MAX_CHARS / 2, (i + 1) * MAX_CHARS / 2);
                textArray[i] += "-";
            } catch (StringIndexOutOfBoundsException e) {
                textArray[i] = text.substring(i * MAX_CHARS / 2);
            }
        }
    }

    public MessageBox(String text, int change) {
        this(text);
        this.change = change;
    }

    public int getChange() {
        return change;
    }

    public boolean isQuestion() {
        return question;
    }

    public char getAnswer() {
        return answer;
    }

    public void draw(Graphics g) {

        g.setColor(Color.WHITE);
        g.fillRect(X, Y, WIDTH, HEIGHT);

        g.setColor(Color.BLACK);
        g.fillRect(X + STROKE, Y + STROKE, WIDTH - STROKE * 2, HEIGHT - STROKE * 2);

        g.setColor(Color.WHITE);
        g.setFont(font);

        for (int i = 0; i < textArray.length; i++) {
            g.drawString(textArray[i], X + 20, Y + (i * 30) + 40);
        }

        if (answers.length == 0) {
            if (displayClick) {
                g.setFont(smallFont);
                g.drawString("click anywhere to continue...", X + 305, Y + 140);
            }
        } else {
            for (int i = 0; i < answers.length; i++) {
                g.drawString(answers[i], X + 420, Y + (i * 30) + 50);
            }

            g.setFont(smallFont);
            g.drawString("press the corresponding key to answer...", X + 250, Y + 140);
        }
    }

}
