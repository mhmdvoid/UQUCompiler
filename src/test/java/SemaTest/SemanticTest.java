package SemaTest;

import ast.ASTNode;
import ast.TranslationUnit;
import junit.framework.TestCase;
import parser.Parser;

import java.io.File;

public class SemanticTest extends TestCase  {
    private TranslationUnit program ;

    public void testSemanticShouldFail() {
        Parser parser;
        var dir = new File("/Users/engmoht/IdeaProjects/UQULexer/src/test/java/sema_fail_test");
        var allFiles = dir.listFiles();
        assert allFiles != null;
        var errorHappened = false;
        for (File allFile : allFiles) {
            parser = new Parser(allFile.getAbsolutePath());

            program = parser.translationUnit();
            program.semaAnalysis();
            for (ASTNode node : program.gTree()) {
                errorHappened |= node.semaError;
            }
        }
        assertTrue(errorHappened);
    }
}

