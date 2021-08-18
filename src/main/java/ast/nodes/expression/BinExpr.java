package ast.nodes.expression;

import ast.nodes.visitor.ExprVisitor;

import java.util.Optional;

/// Binary :: Two. So representing two operand components, i.e, 4 + 3; foo() * x, lhs = rhs;  (*Operator) Two-Operands and the position can vary: Infix, Postfix , etc.
public class BinExpr extends Expr {
    public enum BinExprKind {
        Add, Sub, Multi, Div;
    }
    public Expr lhs, rhs;
    public BinExprKind binKind; // Avoiding name conflict with baseType;

    public BinExpr(Expr lhs, Expr rhs, BinExprKind K) {
        super(ExprKind.Binary);
        this.lhs = lhs;
        this.rhs = rhs;
        binKind = K;
    }

    @Override
    public void dump(int indent) {

    }

    @Override
    public Optional<Expr> accept(ExprVisitor visitor) {
        return visitor.visitBinExpr(this);
    }
}