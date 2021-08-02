package ast.expr_def;

public class BoolLiteral extends Expression {
    String value;
    public BoolLiteral(String value) {
        super(ExprKind.BoolLiteral);
        this.value = value;
    }
}
