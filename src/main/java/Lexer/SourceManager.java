package Lexer;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;




/**
 * A class represent/manager the source buffer
 */

// should manage the line and current position and where the cursor is right not
public final class SourceManager implements Reader {
    private final File consumedFile;

    private final String filePath;


    private StringBuilder bufferContent;
    public SourceManager(String filePath) {
        this.filePath = filePath;

        // TODO: Valid file path; dir path not allowed;
        consumedFile = new File(filePath);


        bufferContent = new StringBuilder();
        // Don;t do that in a production project;

        read();  // that;s implicit client consuming and only beginners like me do it, seniors never do that;
    }


    @Override
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
    public static void main(String[] args) {
        SourceManager srcManager = new SourceManager("/Users/engmoht/IdeaProjects/UQULexer/src/test/java/test_examples/LexerTest2");
        System.out.println(srcManager.bufferContent);
    }

    public StringBuilder getBufferContent() {
        return bufferContent;
    }
}
