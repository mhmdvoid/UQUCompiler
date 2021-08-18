package ast.nodes.expression;

import ast.nodes.Identifier;
import ast.nodes.visitor.ExprVisitor;

import java.util.Optional;

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

    @Override
    public Optional<Expr> accept(ExprVisitor visitor) {
        return visitor.visitUnresolvedRefExpr(this);
    }
}
