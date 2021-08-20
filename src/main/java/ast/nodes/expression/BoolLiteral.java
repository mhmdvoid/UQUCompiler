package ast.nodes.expression;

import ast.nodes.visitor.ExprVisitor;

import java.util.Optional;

public class BoolLiteral extends Expr {
    String value;
    public BoolLiteral(String value) {
        super(ExprKind.BoolLiteral);
        this.value = value;
    }

    @Override
    public void dump(int indent) {

    }

    @Override
    public Expr accept(ExprVisitor visitor) {
        return visitor.visitBoolLiteral(this);
    }
}
