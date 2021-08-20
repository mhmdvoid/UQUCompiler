package ast.nodes;

import ast.nodes.expression.Expr;

// This is not a proper use for now as you say, otherwise use instanceof
public interface ASTNode {
    public  Expr getExpr();
}
