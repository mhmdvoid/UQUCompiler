package ast.decl_def;

import ast.Identifier;
import ast.expr_def.Expression;
import ast.expr_def.FuncBlockExpr;
import ast.type.Type;

import java.util.List;

// All AST nodes should be declared as private constructor !;
public class FuncDecl extends ValueDecl {
    List<ParamDecl> paramDecls;
    public FuncDecl(Identifier identifier, Type type/*, Expression initial*/) {
        super(DeclKind.FuncDecl, identifier, type, null);  // Initial = funcBlock
    }

    public FuncBlockExpr getBlock() {
        return (FuncBlockExpr) initial;
    }
}
