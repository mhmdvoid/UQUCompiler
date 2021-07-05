package LexingTest;

import Lexer.LexerManager;
import Lexer.SourceManager;
import junit.framework.TestCase;
import lang_tokinezer.Cpp;

import java.io.File;


public class LexerTest extends TestCase {

    private LexerManager underTest;


    public void testLexing() {
        // if there's test should fail ?
        // That;s why we need the source manager;

        var testExampleDir = new File("/Users/engmoht/IdeaProjects/UQULexer/src/test/java/java_example_pass");

        var testFiles = testExampleDir.listFiles();

        SourceManager sourceManager;

        var errorHappened = false;
        assert testFiles != null;    //testFile
        for (File testFile : testFiles) {
            underTest = new LexerManager(testFile.getAbsolutePath());
            errorHappened |= underTest.isInError(); // one ture is enough;
            // false |= true ?
        }

        testExampleDir = new File("/Users/engmoht/IdeaProjects/UQULexer/src/test/java/cpp_example_pass");
        testFiles = testExampleDir.listFiles();
        assert testFiles != null;
        for (File testFile : testFiles) {
            underTest = new LexerManager(testFile.getAbsolutePath(), new Cpp());
            errorHappened |= underTest.isInError(); // one ture is enough;
            // false |= true ?
        }
        testFiles = testExampleDir.listFiles();

        assertFalse(errorHappened);
    }
}
