package ast.nodes.declaration;

import ast.nodes.Identifier;
import ast.nodes.expression.Expr;
import ast.type.Type;

public class VarDecl extends ValueDecl {
    public VarDecl(Identifier identifier, Type type, Expr initial) {
        super(DeclKind.VarDecl, identifier, type, initial);
    }
}
