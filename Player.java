import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Player {
   private int x;
   private int y;
   private int width;
   private int height;
   private String direction;
   private Sprite[] horizontalSprites;
   private Sprite[] verticalSprites;
   private int animationCount;
   private int animationDirection;
   private boolean isHorizontal;
   
   public Player(int x, int y, Sprite[] horizontalSprites, Sprite[] verticalSprites) {
      this.width = horizontalSprites[0].getImage().getWidth();
      this.height = horizontalSprites[0].getImage().getHeight();
      this.x = x - width / 2;
      this.y = y - height / 2;
      this.direction = "DOWN";
      this.horizontalSprites = horizontalSprites;
      this.verticalSprites = verticalSprites;
      this.animationCount = 0;
      this.animationDirection = 1;
      this.isHorizontal = true;
   }
   
   public void move(String direction) {
      this.direction = direction;
      System.out.println(direction);
      
      animationCount += animationDirection;
      animationCount %= horizontalSprites.length;
      
      
   }
   
   public void draw(Graphics g) {
      //g.drawRect(x - width / 2, y - height / 2, width, height);
      
      Sprite nextSprite = isHorizontal ? horizontalSprites[animationCount] : verticalSprites[animationCount];
      //g.drawImage(nextSprite.getImage(), x - width / 2, y - height / 2, null);
      
      if (direction.equals("UP")) {
      
      }
      else if (direction.equals("DOWN")) {
         
      }
      else if (direction.equals("LEFT")) {
         g.drawImage(nextSprite.getImage(), x, y, null);
      }
      else if (direction.equals("RIGHT")) {
         g.drawImage(nextSprite.getImage(), x + width, y, -width, height, null);
      }
   }
}