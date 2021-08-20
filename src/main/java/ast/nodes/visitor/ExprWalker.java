package ast.nodes.visitor;

import ast.nodes.ASTNode;
import ast.nodes.declaration.TypeAliasDecl;
import ast.nodes.declaration.ValueDecl;
import ast.nodes.declaration.VarDecl;
import ast.nodes.expression.*;
import ast.nodes.statement.ReturnState;

import java.util.Optional;

public class ExprWalker implements ExprVisitor {
    FunctionalWalker function;
    Object data;

    public ExprWalker(FunctionalWalker function, Object data) {
        this.function = function;
        this.data = data;
    }

    @Override
    public Expr visitIntegerLiteral(IntegerLiteral integerLiteral) {
        return integerLiteral;
    }

    @Override
    public Expr visitBoolLiteral(BoolLiteral boolLiteral) {
        return boolLiteral;

    }

    @Override
    public Expr visitRefDeclExpr(ReferenceDeclExpr referenceDeclExpr) {
        return referenceDeclExpr;
    }

    @Override
    public Expr visitUnresolvedRefExpr(UnresolvedReferenceExpr unresolvedReferenceExpr) {
        return unresolvedReferenceExpr;
    }

    @Override
    public Expr visitBlockExpr(BlockExpr blockExpr) {
        for (ASTNode element : blockExpr.elements) {
            var elementExpr = element.getExpr();

            if (elementExpr == null) return null;

            var fixed = processEach(elementExpr); // var x = x; for?
            if (fixed == null) return null;
            elementExpr = fixed;
        }

        return blockExpr;
    }

    @Override
    public Expr visitBinExpr(BinExpr binExpr) {
        // Before processing we may have unresolved nodes
        // Our goal out of walking to fix and have more correct programs
        // the idea is when done walking we check for nullptr, And other maybe invalid reasons for a node
        // if everything is valid and not null ? we can take the expr as whole and fix it with new process leaves.

        var fixedExpr = processEach(binExpr.lhs);
        if (fixedExpr == null) return null;

        binExpr.lhs = fixedExpr; // save fixedExpr to lhs, So can be mutated.
        fixedExpr = processEach(binExpr.rhs);
        if (fixedExpr == null) return null;

        binExpr.rhs = fixedExpr;
        return null;
    }

    public Expr doIt(Expr dispatch) {
        return processEach(dispatch);
    }

    // Not obvious from optional context.
    private Expr processEach(Expr expr) {
        expr = function.act(expr, WalkOrder.PreOrder, data);
        if (expr != null)
            expr = expr.accept(this); // FIXME:
        if (expr != null)
            expr = function.act(expr, WalkOrder.PostOrder, data);
        return expr;
    }
}