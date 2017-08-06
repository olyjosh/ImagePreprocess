package preprocessing.pro2;

/**
 * @author Joshua Aroke (olyjosh)
 * olyjoshone@gmail.com
 * f12softwares.com
 */
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
 
/**
 *
 * @author nayef
 */
public class Main {
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
     
        BufferedImage image = ImageIO.read(new File("/home/nayef/Desktop/bw.jpg"));
 
        int[][] imageData = new int[image.getHeight()][image.getWidth()];
        Color c;
        for (int y = 0; y < imageData.length; y++) {
            for (int x = 0; x < imageData[y].length; x++) {
 
                if (image.getRGB(x, y) == Color.BLACK.getRGB()) {
                    imageData[y][x] = 1;
                } else {
                    imageData[y][x] = 0;
 
                }
            }
        }
 
        ThinningService thinningService = new ThinningService();
     
        thinningService.doZhangSuenThinning(imageData);
         
        for (int y = 0; y < imageData.length; y++) {
 
            for (int x = 0; x < imageData[y].length; x++) {
 
                if (imageData[y][x] == 1) {
                    image.setRGB(x, y, Color.BLACK.getRGB());
 
                } else {
                    image.setRGB(x, y, Color.WHITE.getRGB());
                }
 
 
            }
        }
 
        ImageIO.write(image, "jpg", new File("/home/nayef/Desktop/bwThin.jpg"));
 
    }
}