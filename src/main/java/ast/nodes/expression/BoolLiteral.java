package ast.nodes.expression;

public class BoolLiteral extends Expr {
    String value;
    public BoolLiteral(String value) {
        super(ExprKind.BoolLiteral);
        this.value = value;
    }

    @Override
    public void dump(int indent) {

    }
}
