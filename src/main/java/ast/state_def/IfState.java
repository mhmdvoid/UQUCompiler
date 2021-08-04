package ast.state_def;

import ast.expr_def.Expression;

/**
 * IF statement node. Ex if (condition) thenPart{}, elsePart {}
 */
public class IfState extends Statement{

    Expression condition;
    Statement thenPart, elsePart;

    public IfState() {
        super(StateKind.IF_STATE);
    }
}
