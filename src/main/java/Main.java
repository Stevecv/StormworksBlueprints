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
    public static boolean colour = false;
    public static boolean ignoreWhite = false;
    public static void main(String[] args) throws IOException {
        System.out.print("Enter File path: ");
        String file = readConsoleLine();
        System.out.println("Would you like colour? [yes|no]");
        String colourStr = readConsoleLine();

        if (colourStr.equalsIgnoreCase("yes")) {
            colour = true;

            System.out.println("Would you like to ignore white? (Any white pixels wont be blocks in-game) [yes|no]");
            String ignoreWhiteStr = readConsoleLine();

            if(ignoreWhiteStr.equalsIgnoreCase("yes")) {
                ignoreWhite = true;
            } else if(ignoreWhiteStr.equalsIgnoreCase("no")) {
                ignoreWhite = false;
            }
        } else if (colourStr.equalsIgnoreCase("no")) {
            colour = false;
        }

        BufferedImage image = ImageIO.read(new File(file));

        System.out.println("Generating xml. Please wait...");
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
