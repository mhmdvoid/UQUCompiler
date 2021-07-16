package ast;

import java.util.List;

/**
 * Root Node in our AST
 */
public class TranslationUnit extends ASTNode {
    List<GlobalMembersNode> globalMembers;


    protected TranslationUnit(int line) {
        super(line);
    }
}
