package SemaTest;

import ast.TranslationUnit;
import junit.framework.TestCase;

public class SemanticTest extends TestCase  {
    private TranslationUnit program ;

//    public void testSemanticShouldFail() {
//        var dir = new File("/Users/engmoht/IdeaProjects/UQULexer/src/test/java/sema_fail_test");
//        var allFiles = dir.listFiles();
//        assert allFiles != null;
//        var errorHappened = false;
//        for (var allFile : allFiles) {
//            var parser = new Parser(allFile.getAbsolutePath());
//
//            program = parser.translationUnit();
//            program.semaAnalysis();
//            for (var node : program.gTree()) {
//                errorHappened |= node.semaError;
//            }
//        }
//        assertTrue(errorHappened);
//    }
}

