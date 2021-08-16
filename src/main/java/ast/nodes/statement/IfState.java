package ast.nodes.statement;

import ast.nodes.expression.Expr;

/**
 * IF statement node. Ex if (condition) thenPart{}, elsePart {}
 */
public class IfState extends Statement{

    Expr condition;
    Statement thenPart, elsePart;

    public IfState() {
        super(StateKind.IF_STATE);
    }
}
