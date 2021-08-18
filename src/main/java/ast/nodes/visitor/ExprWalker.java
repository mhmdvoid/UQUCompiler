package ast.nodes.visitor;

import ast.nodes.expression.*;

import java.util.Optional;

public class ExprWalker implements ExprVisitor {
    FunctionalWalker function;
    Object data;

    public ExprWalker(FunctionalWalker function, Object data) {
        this.function = function;
        this.data = data;
    }

    @Override
    public Optional<Expr> visitIntegerLiteral(IntegerLiteral integerLiteral) {
        return Optional.ofNullable(integerLiteral);
    }

    @Override
    public Optional<Expr> visitBoolLiteral(BoolLiteral boolLiteral) {
        return Optional.ofNullable(boolLiteral);

    }

    @Override
    public Optional<Expr> visitRefDeclExpr(ReferenceDeclExpr referenceDeclExpr) {
        return Optional.ofNullable(referenceDeclExpr);
    }

    @Override
    public Optional<Expr> visitUnresolvedRefExpr(UnresolvedReferenceExpr unresolvedReferenceExpr) {
        return Optional.ofNullable(unresolvedReferenceExpr);
    }

    @Override
    public Optional<Expr> visitBinExpr(BinExpr binExpr) {
        // Before processing we may have unresolved nodes
        // Our goal out of walking to fix and have more correct programs
        // the idea is when done walking we check for nullptr, And other maybe invalid reasons for a node
        // if everything is valid and not null ? we can take the expr as whole and fix it with new process leaves.

        var fixedExpr = processEach(binExpr.lhs);
        if (fixedExpr == null) return Optional.empty();

        binExpr.lhs = fixedExpr; // save fixedExpr to lhs, So can be mutated.
        fixedExpr = processEach(binExpr.rhs);
        if (fixedExpr == null) return Optional.empty();

        binExpr.rhs = fixedExpr;
        return Optional.of(binExpr);
    }

    public Expr doIt(Expr dispatch) {
        return processEach(dispatch);
    }

    // Not obvious from optional context.
    private Expr processEach(Expr expr) {
        expr = function.act(expr, WalkOrder.PreOrder, data);
        if (expr != null)
            expr = expr.accept(this).get(); // FIXME:
        if (expr != null)
            expr = function.act(expr, WalkOrder.PostOrder, data);
        return expr;
    }
}