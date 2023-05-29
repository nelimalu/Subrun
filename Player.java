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

   public boolean collide(int x, int y, Obstacle obstacle) {
      return x < obstacle.getX() + obstacle.getWidth() &&
              x + this.width > obstacle.getX() &&
              y < obstacle.getY() + obstacle.getHeight() &&
              this.height + y > obstacle.getY();
   }

   public String getSide(int x, int y, Obstacle obstacle) {
      int left = obstacle.getX();
      int right = obstacle.getX() + obstacle.getWidth();
      int top = obstacle.getY();
      int bottom = obstacle.getY() + obstacle.getHeight();
      int centerX = x + width / 2;
      int centerY = y + height / 2;

      if (left < centerX && centerX < right) {
         if (centerY < top)
            return "TOP";
         else if (centerY > bottom)
            return "BOTTOM";
      }
      if (top < centerY && centerY < bottom) {
         if (centerX < left)
            return "LEFT";
         else if (centerX > right)
            return "RIGHT";
      }

      if (centerX <= left && centerY <= top) {  // TOP LEFT
         if (Math.abs(left - centerX) < Math.abs(top - centerY))
            return "TOP";
         return "LEFT";
      }
      if (centerX <= left && centerY >= bottom) {  // BOTTOM LEFT
         if (Math.abs(left - centerX) < Math.abs(centerY - bottom))
            return "BOTTOM";
         return "LEFT";
      }
      if (centerX >= right && centerY <= top) {  // TOP RIGHT
         if (Math.abs(centerX - right) < Math.abs(top - centerY))
            return "TOP";
         return "RIGHT";
      }
      if (centerX >= right && centerY >= bottom) {  // BOTTOM RIGHT
         if (Math.abs(centerX - right) < Math.abs(centerY - bottom))
            return "BOTTOM";
         return "RIGHT";
      }
      return "NONE";
   }

   public void draw(Graphics g, boolean[] buttons, Obstacle[] obstacles) {
       g.drawRect(x, y, width, height);
       Sprite nextSprite = !buttons[0] ? horizontalSprites[animationCount / ANIMATION_DELAY_FACTOR] : verticalSprites[animationCount / ANIMATION_DELAY_FACTOR];

      int vDirection = 0;  // vertical direction
      int hDirection = 0;  // horizontal direction
      
      boolean moving = !allFalse(buttons);
      if (moving) {
         animationCount += animationDirection;
         animationCount %= horizontalSprites.length * ANIMATION_DELAY_FACTOR;
      }
      else {
         buttons[prevDirection] = true;
         animationCount = 0;
      }
      
      if (buttons[0]) {
         vDirection += 1;
         g.drawImage(nextSprite.getImage(), x, y, null);
         prevDirection = 0;
      }
      if (buttons[1]) {
         vDirection -= 1;
         g.drawImage(nextSprite.getImage(), x, y, null);
         prevDirection = 1;
      }
      if (buttons[2]) {
         hDirection += 1;
         g.drawImage(nextSprite.getImage(), x, y, null);
         prevDirection = 2;
      }
      if (buttons[3]) {
         hDirection -= 1;
         g.drawImage(nextSprite.getImage(), x + width, y, -width, height, null);
         prevDirection = 3;
      }

      if (moving) {
         boolean collided = false;
         int moveAmountX = hDirection * SPEED;
         int moveAmountY = vDirection * SPEED;

         for (Obstacle obstacle : obstacles) {

            if (collide(x - hDirection * SPEED, y - vDirection * SPEED, obstacle)) {
               String side = getSide(x, y, obstacle);

               if (side.equals("TOP"))
                  moveAmountY = y + height - obstacle.getY(); // get difference from current position to box so the player is right up against the box but not colliding
               else if (side.equals("BOTTOM"))
                  moveAmountY = obstacle.getY() + obstacle.getHeight() - y;
               else if (side.equals("LEFT"))
                  moveAmountX = x + width - obstacle.getX();
               else if (side.equals("RIGHT"))
                  moveAmountX = obstacle.getX() + obstacle.getWidth() - x;
               break;
            }
         }

         for (Obstacle obstacle : obstacles) {
            obstacle.moveX(moveAmountX);
            obstacle.moveY(moveAmountY);
         }
      } else
         buttons[prevDirection] = false;
   }
}