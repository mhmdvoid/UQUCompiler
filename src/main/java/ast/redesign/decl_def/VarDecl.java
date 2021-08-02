package ast.redesign.decl_def;

import ast.redesign.Identifier;
import ast.redesign.expr_def.Expression;
import ast.type.Type;

public class VarDecl extends ValueDecl {
    public VarDecl(Identifier identifier, Type type, Expression initial) {
        super(DeclKind.VarDecl, identifier, type, initial);
    }
}
