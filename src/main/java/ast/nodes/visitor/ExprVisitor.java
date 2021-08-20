package ast.nodes.visitor;

import ast.nodes.expression.*;
import java.util.Optional;

// It should be updated everytime You change AST weather remove or add child to nodes.
// one visiting method per each AST concrete type
public interface ExprVisitor {

    Expr visitIntegerLiteral(IntegerLiteral integerLiteral);

    Expr visitBoolLiteral(BoolLiteral boolLiteral);

    Expr visitRefDeclExpr(ReferenceDeclExpr referenceDeclExpr);

    Expr visitUnresolvedRefExpr(UnresolvedReferenceExpr unresolvedReferenceExpr);

    Expr visitBlockExpr(BlockExpr blockExpr);

    Expr visitBinExpr(BinExpr binExpr);
}