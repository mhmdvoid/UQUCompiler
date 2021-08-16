package ast.nodes.expression;

import ast.nodes.Identifier;

// var x: int = z <- z is unresolved sema later for look up
public class UnresolvedReferenceExpr extends Expr {
    public Identifier identifier;

    public UnresolvedReferenceExpr(Identifier identifier) {
        super(ExprKind.UnresolvedReferenceExpr);
        this.identifier = identifier;
    }

    @Override
    public void dump(int indent) {

    }
}
