package ast.nodes.expression;

import ast.nodes.ASTNode;
import ast.type.Type;

public abstract class Expr extends ASTNode {

    ExprKind kind;   // This tells us subclasses . It's a design pattern used for polymorphism rather Switch statement.
    public Type type;
    public Expr(ExprKind kind) {
        this.kind = kind;
    }
    public abstract void dump(int indent);
}
