package ParserTest;

import junit.framework.TestCase;
import parser.Parser;

import java.io.File;

public class TestParser extends TestCase {

    private Parser parser;

    public void testPassParser() {

        var dir = new File("/Users/engmoht/IdeaProjects/UQULexer/src/test/java/parser_uqulang_test_pass");
        var allFiles = dir.listFiles();
        assert allFiles != null;
        var errorHappened = false;
        for (File allFile : allFiles) {
            parser = new Parser(allFile.getAbsolutePath());
            parser.translationUnit();
            errorHappened |= parser.isInError();

        }

        assertFalse(errorHappened);
    }
    public void testFailParser() {

        var dir = new File("/Users/engmoht/IdeaProjects/UQULexer/src/test/java/parser_uqulang_test_fail");
        var allFiles = dir.listFiles();
        assert allFiles != null;
        var errorHappened = false;
        for (File allFile : allFiles) {
            parser = new Parser(allFile.getAbsolutePath());
            parser.translationUnit();
            errorHappened |= parser.isInError();

        }

        assertTrue(errorHappened);
    }
}
