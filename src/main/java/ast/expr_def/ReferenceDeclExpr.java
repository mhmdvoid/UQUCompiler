package ast.expr_def;

import ast.decl_def.ValueDecl;
// var y: int = 10;
// var x: int = y <- referenceDeclExpr
public class ReferenceDeclExpr extends Expression {
    ValueDecl valueDecl; // value of reference decl
    public ReferenceDeclExpr(ValueDecl valueDecl) {
        super(ExprKind.ReferenceDecl);
        this.valueDecl = valueDecl;
    }

    @Override
    public void dump(int indent) {

    }
}
