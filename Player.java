import javax.swing.*;
import java.awt.*;

public class Player {
   private int x;
   private int y;
   private static final int width = 20;
   private static final int height = 50;
   private String direction;
   
   public Player(int x, int y) {
      this.x = x;
      this.y = y;
      this.direction = "DOWN";
   }
   
   public void move(String direction) {
      this.direction = direction;
      System.out.println(direction);
      if (direction.equals("UP")) {}
   }
   
   public void draw(Graphics g) {
      g.drawRect(x - width / 2, y - height / 2, width, height);
   }
}