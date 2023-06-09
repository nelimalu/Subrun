import java.awt.*;

/**
 * Player class handle movement across the screen
 *
 * <strong>Course info:</strong>
 * ICS4U0 with V. Krasteva
 *
 * @version 1.0
 * @author Luka Jovanovic & Brian Song
 * Created on 2023/05/15
 */
public class Player {
   /** X location of the player */
   private int x;

   /** Y location of the player */
   private int y;

   /** X location of the player before any movements */
   private int originalX;

   /** Y location of the player before any movements */
   private int originalY;

   /** width of the player */
   private int width;

   /** height of the player */
   private int height;

   /** sprites used to animate regular walking */
   private Sprite[] horizontalSprites;

   /** sprites used to animate walking upwards */
   private Sprite[] verticalSprites;

   /** sprites used to animate biking */
   private Sprite[] bikeSprites;

   /** sprite of the player but dead */
   private Sprite deadSprite;

   /** which frame of the animation the player is in */
   private int animationCount;

   /** which direction the animation should move in */
   private int animationDirection;

   /** previous movement direction of the player */
   private int prevDirection;

   /** Factor of delay between the animation and frames: in this case one animation frame is 2 regular frames */
   private static final int ANIMATION_DELAY_FACTOR = 2;

   /** speed of the player in pixels per frame */
   public static final int SPEED = 10;

   /**
    * Constructor for the player with available horizontal and vertical sprites
    * @param x X location of the player
    * @param y Y location of the player
    * @param horizontalSprites Array of horizontal animated sprites
    * @param verticalSprites Array of vertical animated sprites
    */
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

   /**
    * Constructor for the player with available horizontal, vertical sprites, and bike sprites
    * @param x X location of the player
    * @param y Y location of the player
    * @param horizontalSprites Array of horizontal animated sprites
    * @param verticalSprites Array of vertical animated sprites
    * @param bikeSprites Array of animated sprites on a bike
    */
   public Player(int x, int y, Sprite[] horizontalSprites, Sprite[] verticalSprites, Sprite[] bikeSprites) {
      this(x, y, horizontalSprites, verticalSprites);
      this.bikeSprites = bikeSprites;
   }

   /**
    * Constructor for the player with available horizontal, vertical sprites, and bike sprites
    * @param x X location of the player
    * @param y Y location of the player
    * @param horizontalSprites Array of horizontal animated sprites
    * @param verticalSprites Array of vertical animated sprites
    * @param bikeSprites Array of animated sprites on a bike
    * @param deadSprite Sprite of the player but dead
    */
   public Player(int x, int y, Sprite[] horizontalSprites, Sprite[] verticalSprites, Sprite[] bikeSprites, Sprite deadSprite) {
      this(x, y, horizontalSprites, verticalSprites);
      this.bikeSprites = bikeSprites;
      this.deadSprite = deadSprite;
   }

   /**
    * @return The dead sprite
    */
   public Sprite getDeadSprite() {
      return deadSprite;
   }

   /**
    * Resets the player's position to where they started
    */
   public void resetPosition() {
      this.x = originalX - width / 2;
      this.y = originalY - height / 2;
   }

   /**
    * checks if all values in an array are false
    * @param array Array of boolean values
    * @return If every value in the array is false
    */
   public static boolean allFalse(boolean[] array) {
       for (boolean b : array)
         if (b)
            return false;
       return true;
   }

   /**
    * Checks if the player collides with an obstacle
    * @param x X position of the player
    * @param y Y position of the player
    * @param obstacle The obstacle
    * @return if the player is colliding or not
    */
   public boolean collide(int x, int y, Obstacle obstacle) {
      return x < obstacle.getX() + obstacle.getWidth() &&
              x + this.width > obstacle.getX() &&
              y < obstacle.getY() + obstacle.getHeight() &&
              this.height + y > obstacle.getY();
   }

   /**
    * Checks if the player collides with an obstacle except the player is given a smaller hitbox
    * @param obstacle The obstacle it's colliding with
    * @return if the player is colliding or not
    */
   public boolean collideSmall(Obstacle obstacle) {
      return x < obstacle.getX() + obstacle.getWidth() &&
              x + bikeSprites[0].getImage().getWidth() > obstacle.getX() &&
              y + bikeSprites[0].getImage().getHeight() / 2 < obstacle.getY() + obstacle.getHeight() &&
              bikeSprites[0].getImage().getHeight() / 2 + y > obstacle.getY();
   }

   /**
    * @return The default sprite for the player
    */
   public Sprite getSprite() {
      return horizontalSprites[0];
   }

   /**
    * If the player at its current location collides with an obstacle
    * @param obstacle The obstacle it's colliding with
    * @return if the player is colliding or not
    */
   public boolean collide(Obstacle obstacle) {
      return collide(x, y, obstacle);
   }

   /**
    * Get which side of the obstacle the player is hitting
    * @param x The new X position of the player
    * @param y The new Y position of the player
    * @param obstacle The obstacle it's colliding with
    * @return Which side the player collides with
    */
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

   /**
    * Moves the player along the screen
    * @param xDistance X distance to travel
    * @param yDistance Y distance to travel
    */
   public void moveReal(int xDistance, int yDistance) {
      x += xDistance;
      y += yDistance;
   }

   /**
    * Resets the animation counter
    */
   public void resetAnimationCount() {
      animationCount = 0;
   }

   /**
    * Moves the player based on buttons pressed
    * @param buttons Boolean array of which buttons are currently being pressed
    * @param obstacles Array of all collidable obstacles in the game
    * @return X and Y distance travelled by the player legally
    */
   public int[] move(boolean[] buttons, Obstacle[] obstacles) {
      int vDirection = 0;  // vertical direction
      int hDirection = 0;  // horizontal direction

      boolean moving = !allFalse(buttons);

      // find which direction the player is moving in
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

         for (Obstacle obstacle : obstacles) {  // check for collision with each object

            if (collide(x - hDirection * SPEED, y - vDirection * SPEED, obstacle)) {
               String side = getSide(x, y, obstacle);  // find which side it collides with

               switch (side) {
                  case "TOP":
                     moveAmountY = y + height - obstacle.getY(); // get difference from current position to box so the player is right up against the box but not colliding
                     break;
                  case "BOTTOM":
                     moveAmountY = obstacle.getY() + obstacle.getHeight() - y;
                     break;
                  case "LEFT":
                     moveAmountX = x + width - obstacle.getX();
                     break;
                  case "RIGHT":
                     moveAmountX = obstacle.getX() + obstacle.getWidth() - x;
                     break;
               }
            }
         }

         return new int[] {moveAmountX, moveAmountY};  // return the distance legally travelled
      }

      return new int[] {0, 0};
   }

   /**
    * Move the player in the sample biking game, but make sure they stay within the three legal lanes
    * @param buttons Buttons currently being pressed
    * @param lane The lane that the biker is currently in
    * @param bikingGame The biking game object
    */
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

   /**
    * Move the player in the real biking game, but make sure they stay within the three legal lanes
    * @param buttons Buttons currently being pressed
    * @param lane The lane that the biker is currently in
    * @param bikingGame The biking game object
    */
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

   /**
    * Draw the player with the biking animation cycle
    * @param g Graphics object for drawing
    */
   public void drawUp(Graphics g) {
      Sprite nextSprite = bikeSprites[animationCount / ANIMATION_DELAY_FACTOR];
      g.drawImage(nextSprite.getImage(), x, y, null);

      animationCount++;
      if (animationCount > ANIMATION_DELAY_FACTOR)
         animationCount = 0;

   }

   /**
    * Draw the player moving regularly with animations depending on the directoin
    * @param g Graphics object for drawing
    * @param buttons
    */
   public void draw(Graphics g, boolean[] buttons) {
       if (Controller.SHOW_HITBOXES) {
          g.setColor(Color.WHITE);
          g.drawRect(x, y, width, height);
       }
       // next sprite to be drawn in the animatino cycle
       Sprite nextSprite = !buttons[0] ? horizontalSprites[animationCount / ANIMATION_DELAY_FACTOR] : verticalSprites[animationCount / ANIMATION_DELAY_FACTOR];

      boolean moving = !allFalse(buttons);
      if (moving) {  // cycle the animation cycle
         animationCount += animationDirection;
         animationCount %= horizontalSprites.length * ANIMATION_DELAY_FACTOR;
      }
      else {
         buttons[prevDirection] = true;
         animationCount = 0;
      }
      // find which direction the player is moving
      if (buttons[0]) {  // UP
         g.drawImage(nextSprite.getImage(), x, y, null);
      }
      if (buttons[1]) {  // DOWN
         g.drawImage(nextSprite.getImage(), x, y, null);
         prevDirection = 1;
      }
      if (buttons[2]) {  // LEFT
         g.drawImage(nextSprite.getImage(), x, y, null);
         prevDirection = 2;
      }
      if (buttons[3]) {  // RIGHT
         g.drawImage(nextSprite.getImage(), x + width, y, -width, height, null);  // flip image when going right
         prevDirection = 3;
      }

      if (!moving)
         buttons[prevDirection] = false;
   }

   /**
    * @return The player's current X position
    */
   public int getX() {
      return x;
   }

   /**
    * @return The player's current Y position
    */
   public int getY() {
      return y;
   }
}