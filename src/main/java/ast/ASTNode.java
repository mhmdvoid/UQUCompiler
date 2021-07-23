package ast;

import semantic.Context;
import semantic.TypeChecker;

// Should extract common classes for all AST nodes;
public abstract class ASTNode  {

    TypeChecker typeChecker;
    private int line;

    protected ASTNode(int line) {
        this.line = line;
        typeChecker = new TypeChecker();
    }

    public ASTNode semaAnalysis(Context context) {
        return this;
    }

    // Very bad common APi
    protected void dump(int indent) {

    }
    public int getLine() {
        return line;
    }
}
