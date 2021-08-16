package ast.nodes.statement;

import ast.nodes.ASTNode;

public class Statement extends ASTNode {
    StateKind kind;

    public Statement(StateKind kind) {
        this.kind = kind;
    }
}
