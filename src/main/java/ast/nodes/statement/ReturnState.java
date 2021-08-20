package ast.nodes.statement;

import ast.nodes.expression.Expr;

public class ReturnState extends Statement {
    public Expr returnExpr;  // null for void function

    public ReturnState(Expr returnExpr) {
        super(StateKind.RETURN_STATE);
        this.returnExpr = returnExpr;
    }

    // if this is true, mean return statement used with no expr. So later we can correct the program by checking: does this method return void to be legal for use return w/o expr?
    public boolean returnForVoid() {
        return returnExpr == null;
    }

    @Override
    public Expr getExpr() {
        return returnExpr;
    }
}