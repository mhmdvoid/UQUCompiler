package ast;

import java.util.List;

/**
 * Root Node in our AST
 */
public class TranslationUnit extends ASTNode {
    List<GlobalMembersNode> globalMembers;

    public TranslationUnit(List<GlobalMembersNode> globalMembers) {
        this.globalMembers = globalMembers;
        translationUnit = this;
    }
}
