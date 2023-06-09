import java.awt.*;

public class Player {
   private int x;
   private int y;
   private int originalX;
   private int originalY;
   private int width;
   private int height;
   private Sprite[] horizontalSprites;
   private Sprite[] verticalSprites;
   private Sprite[] bikeSprites;
   private int animationCount;
   private int animationDirection;
   private int prevDirection;
   private static final int ANIMATION_DELAY_FACTOR = 2;
   public static final int SPEED = 10;
   
   public Player(int x, int y, Sprite[] horizontalSprites, Sprite[] verticalSprites) {
      this.width = horizontalSprites[0].getImage().getWidth();
      this.height = horizontalSprites[0].getImage().getHeight();
      this.originalX = x;
      this.originalY = y;
      this.x = x - width / 2;
      this.y = y - height / 2;
      this.horizontalSprites = horizontalSprites;
      this.verticalSprites = verticalSprites;
      this.animationCount = 0;
      this.animationDirection = 1;
   }

   public Player(int x, int y, Sprite[] horizontalSprites, Sprite[] verticalSprites, Sprite[] bikeSprites) {
      this(x, y, horizontalSprites, verticalSprites);
      this.bikeSprites = bikeSprites;
   }

   public void resetPosition() {
      this.x = originalX - width / 2;
      this.y = originalY - height / 2;
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

   public boolean collideSmall(Obstacle obstacle) {
      return x < obstacle.getX() + obstacle.getWidth() &&
              x + bikeSprites[0].getImage().getWidth() > obstacle.getX() &&
              y + bikeSprites[0].getImage().getHeight() / 2 < obstacle.getY() + obstacle.getHeight() &&
              bikeSprites[0].getImage().getHeight() / 2 + y > obstacle.getY();
   }

   public boolean collide(Obstacle obstacle) {
      return collide(x, y, obstacle);
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

   public void moveReal(int xDistance, int yDistance) {
      x += xDistance;
      y += yDistance;
   }

   public void resetAnimationCount() {
      animationCount = 0;
   }

   public int[] move(boolean[] buttons, Obstacle[] obstacles) {
      int vDirection = 0;  // vertical direction
      int hDirection = 0;  // horizontal direction

      boolean moving = !allFalse(buttons);

      if (buttons[0]) {
         vDirection += 1;
         prevDirection = 0;
      }
      if (buttons[1]) {
         vDirection -= 1;
         prevDirection = 1;
      }
      if (buttons[2]) {
         hDirection += 1;
         prevDirection = 2;
      }
      if (buttons[3]) {
         hDirection -= 1;
         prevDirection = 3;
      }

      if (moving) {
         int moveAmountX = hDirection * SPEED;
         int moveAmountY = vDirection * SPEED;

         for (Obstacle obstacle : obstacles) {

            if (collide(x - hDirection * SPEED, y - vDirection * SPEED, obstacle)) {
               String side = getSide(x, y, obstacle);

               switch (side) {
                  case "TOP" ->
                          moveAmountY = y + height - obstacle.getY(); // get difference from current position to box so the player is right up against the box but not colliding
                  case "BOTTOM" -> moveAmountY = obstacle.getY() + obstacle.getHeight() - y;
                  case "LEFT" -> moveAmountX = x + width - obstacle.getX();
                  case "RIGHT" -> moveAmountX = obstacle.getX() + obstacle.getWidth() - x;
               }
            }
         }

         return new int[] {moveAmountX, moveAmountY};
      }

      return new int[] {0, 0};
   }

   public void bikingMove(boolean[] buttons, int lane, BikingGame.SampleGame bikingGame) {
      if (buttons[2] && lane < 2) {
         bikingGame.setCurrentLane(bikingGame.getCurrentLane() + 1);
         moveReal(-100, 0);
      }

      if (buttons[3] && lane > 0) {
         bikingGame.setCurrentLane(bikingGame.getCurrentLane() - 1);
         moveReal(100, 0);
      }
   }

   public void bikingMove(boolean[] buttons, int lane, BikingGame bikingGame) {
      if (buttons[2] && lane < 2) {
         bikingGame.setCurrentLane(bikingGame.getCurrentLane() + 1);
         moveReal(-100, 0);
      }

      if (buttons[3] && lane > 0) {
         bikingGame.setCurrentLane(bikingGame.getCurrentLane() - 1);
         moveReal(100, 0);
      }
   }

   public void drawUp(Graphics g) {
      Sprite nextSprite = bikeSprites[animationCount / ANIMATION_DELAY_FACTOR];
      g.drawImage(nextSprite.getImage(), x, y, null);

      animationCount++;
      if (animationCount > ANIMATION_DELAY_FACTOR)
         animationCount = 0;

   }

   public void draw(Graphics g, boolean[] buttons) {
       if (Controller.SHOW_HITBOXES) {
          g.setColor(Color.WHITE);
          g.drawRect(x, y, width, height);
       }
       Sprite nextSprite = !buttons[0] ? horizontalSprites[animationCount / ANIMATION_DELAY_FACTOR] : verticalSprites[animationCount / ANIMATION_DELAY_FACTOR];

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
         g.drawImage(nextSprite.getImage(), x, y, null);
      }
      if (buttons[1]) {
         g.drawImage(nextSprite.getImage(), x, y, null);
         prevDirection = 1;
      }
      if (buttons[2]) {
         g.drawImage(nextSprite.getImage(), x, y, null);
         prevDirection = 2;
      }
      if (buttons[3]) {
         g.drawImage(nextSprite.getImage(), x + width, y, -width, height, null);
         prevDirection = 3;
      }

      if (!moving)
         buttons[prevDirection] = false;
   }

   public int getX() {
      return x;
   }

   public int getY() {
      return y;
   }
}