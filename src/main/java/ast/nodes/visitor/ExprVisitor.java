package ast.nodes.visitor;

import ast.nodes.expression.*;

import java.util.Optional;

public interface ExprVisitor {

    Optional<Expr> visitIntegerLiteral(IntegerLiteral integerLiteral);

    Optional<Expr> visitBoolLiteral(BoolLiteral boolLiteral);

    Optional<Expr> visitRefDeclExpr(ReferenceDeclExpr referenceDeclExpr);

    Optional<Expr> visitUnresolvedRefExpr(UnresolvedReferenceExpr unresolvedReferenceExpr);

    //    Optional<Expr> visitFuncExpr();  // TODO

    Optional<Expr> visitBinExpr(BinExpr binExpr);
}