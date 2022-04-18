import java.io.*;

public class GenerateFile {
    public static String blocks = "";

    /*
    Adds a block to the code
     */
    public void addBlock(String x, String z) {
        String block = "<c><o r=\"1,0,0,0,1,0,0,0,1\" sc=\"6\"><vp x=\"Xv\" y=\"0\" z=\"Zv\"/></o></c>";

        block = block
                .replace("Xv", x)
                .replace("Zv", z);
        blocks = blocks + block;
    }

    /*
    Generate the code, file and writes it to the file
     */
    public void generateFile() throws IOException {
        String before = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><vehicle data_version=\"3\" bodies_id=\"1\"><authors/><bodies><body unique_id=\"1\"><components>";
        String after = "</components></body></bodies><logic_node_links/></vehicle>";

        String code = before + blocks + after;

        System.out.println("Enter a name:");
        String name = Main.readConsoleLine();

        String dataFolder = System.getenv("APPDATA");
        System.out.println(dataFolder + "\\Stormworks\\data\\vehicles\\" + name + ".xml");
        File file = new File(dataFolder + "\\Stormworks\\data\\vehicles\\" + name + ".xml");
        boolean created = file.createNewFile();
        if (!created) {
            System.out.println("A vehicle under this name already exists!");
            return;
        }

        //Write to the file
        FileWriter myWriter = new FileWriter(file);
        myWriter.write(code);
        myWriter.close();

        System.out.println("Generated file in your vehicles folder");
    }
}
