package ast.nodes.declaration;

import ast.nodes.Identifier;
import ast.nodes.expression.FuncBlockExpr;
import ast.type.Type;

import java.util.List;

// All AST nodes should be declared as private constructor !;
public class FuncDecl extends ValueDecl {
    List<ParamDecl> paramDecls;
    public FuncDecl(Identifier identifier, Type type, List<ParamDecl> paramDecls/*, Expression initial*/) {
        super(DeclKind.FuncDecl, identifier, type, null);  // Initial = funcBlock
        this.paramDecls = paramDecls;
    }

    public FuncBlockExpr getBlock() {
        return (FuncBlockExpr) initial;
    }

    @Override
    public void dump(int indent) {
        super.dump(indent);
        for (ParamDecl paramDecl : paramDecls) {
            paramDecl.dump(indent + 2);
        }
    }
}
