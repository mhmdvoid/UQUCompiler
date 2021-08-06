package ast.expr_def;

import ast.Identifier;

// var x: int = z <- z is unresolved sema later for look up
public class UnresolvedReferenceExpr extends Expression {
    public Identifier identifier;

    public UnresolvedReferenceExpr(Identifier identifier) {
        super(ExprKind.UnresolvedReferenceExpr);
        this.identifier = identifier;
    }
}
