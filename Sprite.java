import javax.imageio.ImageIO;
import java.awt.image.*;
import java.awt.*;
import java.io.*;

public class Sprite {
   private BufferedImage image;
   private int scale;
   
   public Sprite(String path) {
      scale = 5;
      try {
         image = ImageIO.read(new File(path));
         Image scaled = image.getScaledInstance(image.getWidth() * scale, image.getHeight() * scale, Image.SCALE_DEFAULT);
         image = convertToBufferedImage(scaled);
      } catch (IOException e) {
         System.out.println("[ERROR] Failed to load image with path: " + path);
      }
      
   }
   
   public Sprite(String path, int scale) {
      this.scale = scale;
      try {
         image = ImageIO.read(new File(path));
         Image scaled = image.getScaledInstance(image.getWidth() * scale, image.getHeight() * scale, Image.SCALE_DEFAULT);
         image = convertToBufferedImage(scaled);
      } catch (IOException e) {
         System.out.println("[ERROR] Failed to load image with path: " + path);
      }
   }

   public BufferedImage scaleImage(int scale) {
      return convertToBufferedImage(image.getScaledInstance(image.getWidth() * scale, image.getHeight() * scale, Image.SCALE_DEFAULT));
   }
   
   public static BufferedImage convertToBufferedImage(Image image)
   {
       BufferedImage newImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
       Graphics2D g = newImage.createGraphics();
       g.drawImage(image, 0, 0, null);
       g.dispose();
       return newImage;
   }
   
   public BufferedImage getImage() {
      return image;
   }

}