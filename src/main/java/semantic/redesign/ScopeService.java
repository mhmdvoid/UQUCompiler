package semantic.redesign;

import ast.nodes.Identifier;
import ast.nodes.declaration.VarDecl;
import ast.nodes.expression.Expr;
import ast.type.Type;

public class ScopeService {

    static ScopeService shared;
    private static class STATIC { public static final ScopeService service = new ScopeService(); }

    private ScopeService() {
        ADTScope.getInstance().enterScope();
    }

    public static ScopeService init() { return shared = STATIC.service; }

    public void nestedScope() {
        ADTScope.getInstance().enterScope(); // FIXME- Entering scope should be something really important, As we might get subtree; we need to exit it as well.
    }

    public VarDecl varDeclScope(Identifier identifier, Type type, Expr init) {
        var decl = ADTScope.getInstance().lookupCurrent(identifier);
        if (decl != null) {
            System.err.println("Error redefinition");
            return (VarDecl) decl;
        }
        decl = new VarDecl(identifier, type, init);
        ADTScope.getInstance().addValueDecl(identifier, decl);
        return (VarDecl) decl;
    }
}
