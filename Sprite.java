import javax.imageio.ImageIO;
import java.awt.image.*;
import java.awt.*;
import java.io.*;

/**
 * Sprite class to make loading and scaling images easy
 *
 * <strong>Course info:</strong>
 * ICS4U0 with V. Krasteva
 *
 * @version 1.0
 * @author [80%] Luka Jovanovic & [20%] Brian Song
 * Created on 2023/05/16
 * Luka: scale factor, file import, buffered image
 * Brian: constructors
 */
public class Sprite {
   /** buffered image that is used when drawing on a frame */
   private BufferedImage image;

   /** how much to scale the image by */
   private int scale;

   /**
    * Load the image and scale it by a default value of 5
    * @param path Path of the image
    */
   public Sprite(String path) {
      scale = 5;
      try {
         image = ImageIO.read(Sprite.class.getResource("/" + path));
         Image scaled = image.getScaledInstance(image.getWidth() * scale, image.getHeight() * scale, Image.SCALE_DEFAULT);
         image = convertToBufferedImage(scaled);
      } catch (IOException e) {
         System.out.println("[ERROR] Failed to load image with path: " + path);
      }
      
   }

   /**
    * Load the image and scale it by an inputted value
    * @param path Path of the image
    * @param scale The scale of the image
    */
   public Sprite(String path, int scale) {
      this.scale = scale;
      try {
         image = ImageIO.read(Sprite.class.getResource("/" + path));
         Image scaled = image.getScaledInstance(image.getWidth() * scale, image.getHeight() * scale, Image.SCALE_DEFAULT);
         image = convertToBufferedImage(scaled);
      } catch (IOException e) {
         System.out.println("[ERROR] Failed to load image with path: " + path);
      }
   }

   /**
    * Take the image from the sprite object and scale it to a new size, then return it as a buffer
    * @param scale The scale of the image
    * @return the buffered image that can be used for drawing on a jswing frame
    */
   public BufferedImage scaleImage(int scale) {
      return convertToBufferedImage(image.getScaledInstance(image.getWidth() * scale, image.getHeight() * scale, Image.SCALE_DEFAULT));
   }

   /**
    * Takes an image and converts it to a buffer
    * @param image Image to be converted
    * @return buffered image
    */
   public static BufferedImage convertToBufferedImage(Image image)
   {
       BufferedImage newImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
       Graphics2D g = newImage.createGraphics();
       g.drawImage(image, 0, 0, null);
       g.dispose();
       return newImage;
   }

   /**
    * @return buffered version of the image that can be used on jswing frames
    */
   public BufferedImage getImage() {
      return image;
   }

}