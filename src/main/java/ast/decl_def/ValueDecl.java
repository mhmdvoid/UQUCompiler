package ast.decl_def;

import ast.Identifier;
import ast.expr_def.Expression;
import ast.type.Type;

public class ValueDecl extends Decl{
    public Identifier identifier;
    public Type type;
    public Expression initial;

    public ValueDecl(DeclKind kind, Identifier identifier, Type type, Expression initial) {
        super(kind);
        this.identifier = identifier;
        this.type = type;
        this.initial = initial;
    }
}
