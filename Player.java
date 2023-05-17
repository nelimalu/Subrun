import javax.swing.*;
import java.awt.*;

public class Player {
   private int x;
   private int y;
   private int width;
   private int height;
   private Sprite[] horizontalSprites;
   private Sprite[] verticalSprites;
   private int animationCount;
   private int animationDirection;
   private int prevDirection;
   
   private static final int ANIMATION_DELAY_FACTOR = 2;
   public static final int SPEED = 5;
   
   public Player(int x, int y, Sprite[] horizontalSprites, Sprite[] verticalSprites) {
      this.width = horizontalSprites[0].getImage().getWidth();
      this.height = horizontalSprites[0].getImage().getHeight();
      this.x = x - width / 2;
      this.y = y - height / 2;
      this.horizontalSprites = horizontalSprites;
      this.verticalSprites = verticalSprites;
      this.animationCount = 0;
      this.animationDirection = 1;
   }
   
   public static boolean allFalse(boolean[] array) {
       for (boolean b : array)
         if (b)
            return false;
       return true;
   }
   
   public void draw(Graphics g, boolean[] buttons, Obstacle[] obstacles) {
      //g.drawRect(x - width / 2, y - height / 2, width, height);
       Sprite nextSprite = !buttons[0] ? horizontalSprites[animationCount / ANIMATION_DELAY_FACTOR] : verticalSprites[animationCount / ANIMATION_DELAY_FACTOR];

      
      int vDirection = 0;  // vertical direction
      int hDirection = 0;  // horizontal direction
      
      boolean moving = !allFalse(buttons);
      System.out.println(java.util.Arrays.toString(buttons));
      if (moving) {
         animationCount += animationDirection;
         animationCount %= horizontalSprites.length * ANIMATION_DELAY_FACTOR;
      }
      else {
         buttons[prevDirection] = true;
         animationCount = 0;
      }
      
      if (buttons[0]) {
         vDirection = 1;
         g.drawImage(nextSprite.getImage(), x, y, null);
         prevDirection = 0;
      }
      if (buttons[1]) {
         vDirection = -1;
         g.drawImage(nextSprite.getImage(), x, y, null);
         prevDirection = 1;
      }
      if (buttons[2]) {
         hDirection = 1;
         g.drawImage(nextSprite.getImage(), x, y, null);
         prevDirection = 2;
      }
      if (buttons[3]) {
         hDirection = -1;
         g.drawImage(nextSprite.getImage(), x + width, y, -width, height, null);
         prevDirection = 3;
      }
      
      if (moving) {
         for (Obstacle obstacle : obstacles) {
            obstacle.moveX(hDirection * SPEED);
            obstacle.moveY(vDirection * SPEED);
         }
      } else 
         buttons[prevDirection] = false;
   }
}