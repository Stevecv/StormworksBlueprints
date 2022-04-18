import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    /*
    Main file
     */
    public static void main(String[] args) throws IOException {
        System.out.print("Enter File path: ");
        String file = readConsoleLine();

        BufferedImage image = ImageIO.read(new File(file));

        new Image().sortImage(image);

        new GenerateFile().generateFile();
    }

    /*
    Reads the last console line as input from the user
     */
    public static String readConsoleLine() throws IOException {
        InputStreamReader streamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        String ret = bufferedReader.readLine();

        return ret;
    }
}
