package ast;

// Should extract common classes for all AST nodes;
public abstract class ASTNode {

    private int line;

    protected ASTNode(int line) {
        this.line = line;
    }

    public int getLine() {
        return line;
    }
}
