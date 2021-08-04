package ast.state_def;

import ast.redesign.ASTNode;

public class Statement extends ASTNode {
    StateKind kind;

    public Statement(StateKind kind) {
        this.kind = kind;
    }
}
