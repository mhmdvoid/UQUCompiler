package ast.expr_def;

import ast.type.Type;

public class Expression {

    ExprKind kind;   // This tells us subclasses . It's a design pattern used for polymorphism rather Switch statement.
    public Type type;
    public Expression(ExprKind kind) {
        this.kind = kind;
    }
}
