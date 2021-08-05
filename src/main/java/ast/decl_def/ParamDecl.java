package ast.decl_def;

import ast.Identifier;
import ast.expr_def.Expression;
import ast.type.Type;

public class ParamDecl extends ValueDecl {

    public ParamDecl(Identifier identifier, Type type) {
        super(DeclKind.FormalDecl, identifier, type, null);  // TODO: 8/4/21 param initial value shouldn't be null, Need support for default values.
    }
}
