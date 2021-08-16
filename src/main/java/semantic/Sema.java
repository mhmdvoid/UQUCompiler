package semantic;

import ast.nodes.ASTInfo;

// S.dec.actOn(); <- Create a new node saved in ASTOwn;
public class Sema {

    public ASTInfo astContext;

    public SemaDecl decl;
    public SemaExpr expr;
    public SemaType type;
    public SemaState statement;
    public Sema(ASTInfo astContext) {
        this.astContext = astContext;
        decl = new SemaDecl(this);
        type = new SemaType(this);
        expr = new SemaExpr(this);
        statement = new SemaState(this);
    }

}
