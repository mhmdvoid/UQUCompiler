package ast.nodes.statement;

import ast.nodes.ASTNode;

public abstract class Statement implements ASTNode {
    StateKind kind;

    public Statement(StateKind kind) {
        this.kind = kind;
    }
}
