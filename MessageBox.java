import java.awt.*;

/**
 * MessageBox class to make displaying dialogue and prompts easily
 *
 * <strong>Course info:</strong>
 * ICS4U0 with V. Krasteva
 *
 * @version 1.0
 * @author [100%] Luka Jovanovic & [0%] Brian Song
 * Luka: design, processing text
 * Created on 2023/06/05
 */
public class MessageBox {

    /** text to be dispalyed */
    private String text;

    /** text to be dispalyed formatted into an array based on length */
    private String[] textArray;

    /** array of answers to a question text box */
    private String[] answers;

    /** question to be asked */
    private boolean question;

    /** answer to the question */
    private char answer;

    /** how many dialogue boxes to switch by after skipping */
    private int change;

    /** if the "click anywhere to continue" message should be shown */
    private boolean displayClick;

    /** X position of the box */
    private static final int X = 5;

    /** Y position of the box */
    private static final int Y = 300;

    /** width of the box */
    private static final int WIDTH = 775;

    /** height of the box */
    private static final int HEIGHT = 160;

    /** border thickness of the box */
    private static final int STROKE = 10;

    /** maximum characters in a line */
    private static final int MAX_CHARS = 60;

    /** regular font used in the box */
    private static final Font font = new Font("Monospaced", Font.BOLD, 20);

    /** smaller font used for additional information */
    private static final Font smallFont = new Font("Monospaced", Font.BOLD, 10);

    /**
     * constructor for a regular text box
     * parses the text and splits it into an array based on the MAX_CHARS value
     * @param text The text to be displayed
     */
    public MessageBox(String text) {
        this.text = text;
        this.answers = new String[0];
        this.question = false;
        this.change = 1;
        this.displayClick = true;

        this.textArray = new String[text.length() / MAX_CHARS + 1];  // array with length of predicted number of lines required
        for (int i = 0; i < text.length() / MAX_CHARS + 1; i++) {
            try {
                textArray[i] = text.substring(i * MAX_CHARS, (i + 1) * MAX_CHARS);  // add each line based on the number of characters that fit
                textArray[i] += "-";  // hyphenate to accomodate for split words
            } catch (StringIndexOutOfBoundsException e) {  // if the index is out of bounds then we take all that is left over
                textArray[i] = text.substring(i * MAX_CHARS);
            }
        }

    }

    /**
     * constructor for a box that is already split into lines and does not need formatting
     * @param text pre-formatted text into lines
     */
    public MessageBox(String[] text) {
        this.answers = new String[0];
        this.question = false;
        this.change = 1;
        this.textArray = text;
        this.displayClick = false;
    }

    /**
     * constructor for a multiple choice box
     * Splits the question into a half as wide box to accommodate answers
     * @param text Question to be asked
     * @param answers Possible answers to the question
     * @param answer The answer to the question
     */
    public MessageBox(String text, String[] answers, char answer) {
        this.text = text;
        this.answers = answers;
        this.question = true;
        this.answer = answer;
        this.change = 1;
        this.displayClick = true;

        this.textArray = new String[(text.length() / (MAX_CHARS / 2))+ 1];  // array with length of predicted number of lines required
        for (int i = 0; i < textArray.length; i++) {  // add each line based on the number of characters that fit
            try {
                textArray[i] = text.substring(i * MAX_CHARS / 2, (i + 1) * MAX_CHARS / 2);  // add each line based on the number of characters that fit
                textArray[i] += "-";  // hyphenate to accomodate for split words
            } catch (StringIndexOutOfBoundsException e) {
                textArray[i] = text.substring(i * MAX_CHARS / 2);  // if the index is out of bounds then we take all that is left over
            }
        }
    }

    /**
     * Message box constructor when we want to skip to a specific dialogue box after answering
     * @param text Text to be displayed
     * @param change Number of dialogue boxes to skip
     */
    public MessageBox(String text, int change) {
        this(text);
        this.change = change;
    }

    /**
     * Change an index of the text array
     * @param text New text at the index
     * @param index index to change at
     */
    public void setTextArray(String text, int index) {
        textArray[index] = text;
    }

    /**
     * @return The number of dialogue boxes to be skipped
     */
    public int getChange() {
        return change;
    }

    /**
     * @return Is the box a question?
     */
    public boolean isQuestion() {
        return question;
    }

    /**
     * @return The answer to the question
     */
    public char getAnswer() {
        return answer;
    }

    /**
     * Draw the message box with the properly formatted lines
     * @param g Graphics object for drawing
     */
    public void draw(Graphics g) {

        // draw the box
        g.setColor(Color.WHITE);
        g.fillRect(X, Y, WIDTH, HEIGHT);

        g.setColor(Color.BLACK);
        g.fillRect(X + STROKE, Y + STROKE, WIDTH - STROKE * 2, HEIGHT - STROKE * 2);

        g.setColor(Color.WHITE);
        g.setFont(font);

        // draw each line of text
        for (int i = 0; i < textArray.length; i++) {
            g.drawString(textArray[i], X + 20, Y + (i * 30) + 40);
        }

        if (answers.length == 0) {  // if it's not a question
            if (displayClick) {  // if we want the click to continue message
                g.setFont(smallFont);
                g.drawString("click anywhere to continue...", X + 305, Y + 140);
            }
        } else {  // if it is a question
            for (int i = 0; i < answers.length; i++) {  // draw the answers
                g.drawString(answers[i], X + 420, Y + (i * 30) + 50);
            }

            g.setFont(smallFont);
            g.drawString("press the corresponding key to answer...", X + 250, Y + 140);
        }
    }

}
