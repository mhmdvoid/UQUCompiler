package parser_test;

import ast.nodes.ASTInfo;
import junit.framework.TestCase;
import parser.Parser;

import java.io.File;
import java.util.Objects;

public class TestParser extends TestCase {
    private Parser parser_under_test;

    public void testParsingShouldPass() {
        var passDir = new File("/Users/engmoht/IdeaProjects/UQUCompiler/src/test/java/parser_test/pass_parser");
        assert passDir.isDirectory();
        var errorHappened = false;
        for (File file : Objects.requireNonNull(passDir.listFiles())) {
            parser_under_test = new Parser(file.getAbsolutePath(), new ASTInfo());
            parser_under_test.parseTranslateUnitDecl();
            System.out.println(file.getName());
            errorHappened |= parser_under_test.isInError();
        }
        assertFalse(errorHappened);
    }

    public void testParsingShouldFail() {
        var failDir = new File("/Users/engmoht/IdeaProjects/UQUCompiler/src/test/java/parser_test/fail_parser");
        assert failDir.isDirectory();
        var errorHappened = false;
        for (File file : Objects.requireNonNull(failDir.listFiles())) {
            parser_under_test = new Parser(file.getAbsolutePath(), new ASTInfo());
            parser_under_test.parseTranslateUnitDecl();
            errorHappened |= parser_under_test.isInError();
        }
        assertTrue(errorHappened);
    }
}
