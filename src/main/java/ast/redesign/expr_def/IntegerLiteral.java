package ast.redesign.expr_def;

public class IntegerLiteral extends Expression {
    String value;
    public IntegerLiteral(String value) {
        super(ExprKind.IntegerLiteral);
        this.value = value;
    }
}
