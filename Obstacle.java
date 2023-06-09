import javax.swing.*;
import java.awt.*;

/**
 * Obstacle class to make drawing sprites and checking for collisions easier
 *
 * <strong>Course info:</strong>
 * ICS4U0 with V. Krasteva
 *
 * @version 1.0
 * @author Luka Jovanovic & Brian Song
 * Created on 2023/05/17
 */
public class Obstacle {
   /** X position of the obstacle */
   private int x;

   /** Y position of the obstacle */
   private int y;

   /** width of the obstacle */
   private int width;

   /** height of the obstacle */
   private int height;

   /** sprite to be drawn in replacement of a solid colour */
   private Sprite sprite;

   /** colour to be used in replacement of black */
   private Color colour;

   /**
    * Obstacle constructor that uses a sprite
    * @param x X position of the obstacle
    * @param y Y position of the obstacle
    * @param sprite Sprite to be used when drawing
    */
   public Obstacle(int x, int y, Sprite sprite) {
      this.x = x;
      this.y = y;
      this.width = sprite.getImage().getWidth();  // width of the sprite
      this.height = sprite.getImage().getHeight();  // height of the sprite
      this.sprite = sprite;
      this.colour = Color.BLACK;
   }

   /**
    * Obstacle constructor without a colour
    * @param x X position of the obstacle
    * @param y Y position of the obstacle
    * @param width width of the obstacle
    * @param height height of the obstacle
    */
   public Obstacle(int x, int y, int width, int height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.colour = Color.BLACK;  // default black colour
   }

   /**
    * Obstacle constructor with a specified colour
    * @param x X position of the obstacle
    * @param y Y position of the obstacle
    * @param width width of the obstacle
    * @param height height of the obstacle
    * @param colour Colour of the obstacle
    */
   public Obstacle(int x, int y, int width, int height, Color colour) {
      this(x, y, width, height);
      this.colour = colour;
   }

   /**
    * Draw the obstacle using a sprite if it's available, otherwise solid colour
    * @param g Graphics object for drawing
    */
   public void draw(Graphics g) {
      g.setColor(colour);
      if (this.sprite == null)
         g.fillRect(x, y, width, height);
      else
         g.drawImage(sprite.getImage(), x, y, null);
   }

   /**
    * Move the obstacle by a specific amount along the X axis
    * @param distance distance to be moved
    */
   public void moveX(int distance) {
      x += distance;
   }

   /**
    * Move the obstacle by a specific amount along the Y axis
    * @param distance distance to be moved
    */
   public void moveY(int distance) {
      y += distance;
   }

   /**
    * Set the Y location of the obstacle
    * @param value The new Y value of the obstacle
    */
   public void setY(int value) {
      y = value;
   }

   /**
    * @return the X location of the obstacle
    */
   public int getX() {
      return x;
   }

   /**
    * @return the Y location of the obstacle
    */
   public int getY() {
      return y;
   }

   /**
    * @return the sprite used to draw the obstacle
    */
   public Sprite getSprite() { return sprite; }

   /**
    * @return the width of the obstacle
    */
   public int getWidth() {
      return width;
   }

   /**
    * @return the height of the obstacle
    */
   public int getHeight() {
      return height;
   }
}