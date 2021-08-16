package lexer_test;

import junit.framework.TestCase;
import lexer.Lexer;

import java.io.File;
import java.util.Objects;

public class TestLexer extends TestCase {
    private Lexer lexer_under_test;

    public void testLexerShouldPass() {
        var errorHappened = false;
        var passDir = new File("/Users/engmoht/IdeaProjects/UQUCompiler/src/test/java/lexer_test/pass_lexer");
        assert passDir.isDirectory();
        for (File file : Objects.requireNonNull(passDir.listFiles())) {
            lexer_under_test = new Lexer(file.getAbsolutePath());
            errorHappened |= lexer_under_test.isInError();
        }
        assertFalse(errorHappened);
    }
    public void testLexerShouldFail() {
        var errorHappened = false;
        var failDir = new File("/Users/engmoht/IdeaProjects/UQUCompiler/src/test/java/lexer_test/fail_lexer");
        assert failDir.isDirectory();
        for (File file : Objects.requireNonNull(failDir.listFiles())) {
            lexer_under_test = new Lexer(file.getAbsolutePath());
            errorHappened |= lexer_under_test.isInError();
        }
        assertTrue(errorHappened);
    }
}
