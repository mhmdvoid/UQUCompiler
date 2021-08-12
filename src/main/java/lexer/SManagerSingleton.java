package lexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SManagerSingleton {
    private static SManagerSingleton singleton;
    private final File consumedFile;

    private final String filePath;
    public StringBuilder bufferContent;
    private SManagerSingleton(String filePath) {
        this.filePath = filePath;

        // TODO: Valid file path; dir path not allowed;
        consumedFile = new File(filePath);


        bufferContent = new StringBuilder();
        // Don;t do that in a production project;

        read();  // that;s implicit client consuming and only beginners like me do it, seniors never do that;
    }
    public static SManagerSingleton shared(String filePath) {
        if (singleton == null) singleton = new SManagerSingleton(filePath);
        return singleton;
    }
    public static SManagerSingleton shared() {
        return singleton;
    }

    public void read() {
        try {
            Scanner myReader = new Scanner(consumedFile);
            boolean first = true;  // Fixme: Should find another/proper way to read
            while (myReader.hasNextLine()) {
                if (!first) {
                    bufferContent.append('\n');
                }
                String data = myReader.nextLine();
                bufferContent.append(data);
                first = false;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public String srcCode(){ return bufferContent.toString(); }
}

// The source holder which's the subsystem somponent in this serivce need to be access throughly
// Lexer, Char manager needs to access it
// char manager should be called by lexer.

