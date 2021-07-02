package LexingTest;

import Lexer.LexerManager;
import Lexer.SourceManager;
import junit.framework.TestCase;

import java.io.File;


public class LexerTest extends TestCase {

    private LexerManager underTest;


    public void testLexing() {
        // if there's test should fail ?
        // That;s why we need the source manager;

        var testExampleDir = new File("/Users/engmoht/IdeaProjects/UQULexer/src/test/java/test_examples");

        var testFiles = testExampleDir.listFiles();

        SourceManager sourceManager;

        var errorHappened = false;
        assert testFiles != null;
        for (File testFile : testFiles) {
            sourceManager = new SourceManager(testFile.getAbsolutePath());
            underTest = new LexerManager(sourceManager.getBufferContent().toString());
            errorHappened |= underTest.isInError(); // one ture is enough;
            // false |= true ?
        }

        assertFalse(errorHappened);
    }
}
