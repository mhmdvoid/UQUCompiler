package ast.nodes.expression;

import ast.nodes.visitor.ExprVisitor;

import java.util.Optional;

public class BinExpr extends Expr {
    public BinExpr() {
        super(ExprKind.Binary);
    }

    @Override
    public void dump(int indent) {

    }

    @Override
    public Optional<Expr> accept(ExprVisitor visitor) {
        return visitor.visitBinExpr(this);
    }
}
