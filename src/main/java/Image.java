import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;

public class Image {
    /*
    Takes the image, goes through each pixel, sees if it's a line and adds it as a block
     */
    public void sortImage(BufferedImage inputImage) {
        BufferedImage image = new NoiseFilter().performDenoise(inputImage);

        //Get width/height to check every pixel
        int width = image.getWidth();
        int height = image.getHeight();



        //Go through pixel by pixel
        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color c = new Color(image.getRGB(x, y));

                //Get the color
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();

                //Check if the pixel is a black line
                if(r <= 245 && g <= 245 && b <= 245) {
                    new GenerateFile().addBlock(String.valueOf(x), String.valueOf(y));
                }
            }
        }
    }
}
