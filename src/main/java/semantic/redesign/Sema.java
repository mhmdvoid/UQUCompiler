package semantic.redesign;

import ast.redesign.ASTOwn;

// S.dec.actOn(); <- Create a new node saved in ASTOwn;
public class Sema {

    public ASTOwn astContext;

    public SemaDecl decl;
    public SemaExpr expr;
    public SemaType type;
    public Sema(ASTOwn astContext) {
        this.astContext = astContext;
        decl = new SemaDecl(this);
        type = new SemaType(this);

    }

}
