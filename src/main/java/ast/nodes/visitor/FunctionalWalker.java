package ast.nodes.visitor;

import ast.nodes.expression.Expr;

@FunctionalInterface
public interface FunctionalWalker {
    <T> Expr act(Expr expr, WalkOrder order, T data);
}
