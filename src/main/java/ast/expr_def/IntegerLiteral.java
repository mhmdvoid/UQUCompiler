package ast.expr_def;

import compile.utils.IndentationKind;
import compile.utils.Indenter;

public class IntegerLiteral extends Expression {
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
}
