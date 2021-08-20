package ast.nodes.declaration;


import ast.nodes.expression.Expr;

public class MultiCommentDecl extends Decl {
    public MultiCommentDecl() {
        super(DeclKind.MultCommDecl);
    }

    @Override
    public void dump(int indent) {

    }

    @Override
    public Expr getExpr() {
        return null; // Clearly a bug FIXME
    }
}
