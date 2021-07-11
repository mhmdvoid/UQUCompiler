package LexingTest;

import lexer.LexerManager;
import lexer.SourceManager;
import junit.framework.TestCase;

import java.io.File;


public class LexerTest extends TestCase {

    private LexerManager underTest;


    public void testLexing() {
        // if there's test should fail ?
        // That;s why we need the source manager;

        var testExampleDir = new File("/Users/engmoht/IdeaProjects/UQULexer/src/test/java/uqulang_test_pass");

        var testFiles = testExampleDir.listFiles();

        SourceManager sourceManager;

        var errorHappened = false;
        assert testFiles != null;    //testFile
        for (File testFile : testFiles) {
            underTest = new LexerManager(testFile.getAbsolutePath());
            errorHappened |= underTest.isInError(); // one ture is enough;
            // false |= true ?
        }

        assertFalse(errorHappened);
    }
}
