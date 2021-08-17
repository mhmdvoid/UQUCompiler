package ast.nodes.expression;

import ast.nodes.declaration.ValueDecl;
import ast.nodes.visitor.ExprVisitor;

import java.util.Optional;

// var y: int = 10;
// var x: int = y <- referenceDeclExpr
public class ReferenceDeclExpr extends Expr {
    ValueDecl valueDecl; // value of reference decl
    public ReferenceDeclExpr(ValueDecl valueDecl) {
        super(ExprKind.ReferenceDecl);
        this.valueDecl = valueDecl;
    }

    @Override
    public void dump(int indent) {

    }

    @Override
    public Optional<Expr> accept(ExprVisitor visitor) {
        return visitor.visitRefDeclExpr(this);
    }
}
