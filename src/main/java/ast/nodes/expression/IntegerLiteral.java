package ast.nodes.expression;

import ast.nodes.visitor.ExprVisitor;
import compile.utils.IndentationKind;
import compile.utils.Indenter;

import java.util.Optional;

public class IntegerLiteral extends Expr {
    String value;
    public IntegerLiteral(String value) {
        super(ExprKind.IntegerLiteral);
        this.value = value;
    }

    @Override
    public void dump(int indent) {
        Indenter.indentWithShape(indent, IndentationKind.WhiteSpace);
        System.out.println(value);
    }

    @Override
    public Optional<Expr> accept(ExprVisitor visitor) {
        return visitor.visitIntegerLiteral(this);
    }
}
