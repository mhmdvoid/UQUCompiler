package ast.redesign.expr_def;

public class Expression {

    ExprKind kind;   // This tells us subclasses . It's a design pattern used for polymorphism rather Switch statement.

    public Expression(ExprKind kind) {
        this.kind = kind;
    }
}
