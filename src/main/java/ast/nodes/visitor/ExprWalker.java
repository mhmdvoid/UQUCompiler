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
        return Optional.empty();
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