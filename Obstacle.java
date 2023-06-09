import javax.swing.*;
import java.awt.*;

public class Obstacle {
   private int x;
   private int y;
   private int width;
   private int height;
   private Sprite sprite;
   private Color colour;
   
   public Obstacle(int x, int y, Sprite sprite) {
      this.x = x;
      this.y = y;
      this.width = sprite.getImage().getWidth();
      this.height = sprite.getImage().getHeight();
      this.sprite = sprite;
      this.colour = Color.BLACK;
   }

   public Obstacle(int x, int y, int width, int height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.colour = Color.BLACK;
   }

   public Obstacle(int x, int y, int width, int height, Color colour) {
      this(x, y, width, height);
      this.colour = colour;
   }
   
   public void draw(Graphics g) {
      g.setColor(colour);
      if (this.sprite == null)
         g.fillRect(x, y, width, height);
      else
         g.drawImage(sprite.getImage(), x, y, null);
   }
   
   public void moveX(int distance) {
      x += distance;
   }
   
   public void moveY(int distance) {
      y += distance;
   }
   public void setY(int value) {
      y = value;
   }
   public int getX() {
      return x;
   }
   public int getY() {
      return y;
   }
   public Sprite getSprite() { return sprite; }
   public int getWidth() {
      return width;
   }
   public int getHeight() {
      return height;
   }
}