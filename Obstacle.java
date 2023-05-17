import javax.swing.*;
import java.awt.*;

public class Obstacle {
   private int x;
   private int y;
   private int width;
   private int height;
   private Sprite sprite;
   
   public Obstacle(int x, int y, Sprite sprite) {
      this.x = x;
      this.y = y;
      this.width = sprite.getImage().getWidth();
      this.height = sprite.getImage().getHeight();
      this.sprite = sprite;
   }
   
   public void draw(Graphics g) {
      g.drawImage(sprite.getImage(), x, y, null);
   }
   
   public void moveX(int distance) {
      x += distance;
   }
   
   public void moveY(int distance) {
      y += distance;
   }
}