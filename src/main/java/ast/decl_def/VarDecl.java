package ast.decl_def;

import ast.Identifier;
import ast.expr_def.Expression;
import ast.type.Type;

public class VarDecl extends ValueDecl {
    public VarDecl(Identifier identifier, Type type, Expression initial) {
        super(DeclKind.VarDecl, identifier, type, initial);
    }
}
