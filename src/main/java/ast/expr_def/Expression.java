package ast.expr_def;

import ast.redesign.ASTNode;
import ast.type.Type;

public abstract class Expression extends ASTNode {

    ExprKind kind;   // This tells us subclasses . It's a design pattern used for polymorphism rather Switch statement.
    public Type type;
    public Expression(ExprKind kind) {
        this.kind = kind;
    }
    public abstract void dump(int indent);
}
