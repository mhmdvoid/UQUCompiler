package ast.nodes.declaration;

import ast.nodes.Identifier;
import ast.nodes.expression.BlockExpr;
import ast.type.Type;

import java.util.List;

// All AST nodes should be declared as private constructor !;
public class FuncDecl extends ValueDecl {
    List<ParamDecl> paramDecls;
    public FuncDecl(Identifier identifier, Type type, List<ParamDecl> paramDecls/*, Expression initial*/) {
        super(DeclKind.FuncDecl, identifier, type, null);  // Initial = funcBlock
        this.paramDecls = paramDecls;
    }

    public BlockExpr getBlock() {
        return (BlockExpr) initial;
    }

    @Override
    public void dump(int indent) {
        super.dump(indent);
        for (ParamDecl paramDecl : paramDecls) {
            paramDecl.dump(indent + 2);
        }
    }
}
