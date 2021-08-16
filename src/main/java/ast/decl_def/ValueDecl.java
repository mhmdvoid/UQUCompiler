package ast.decl_def;

import ast.Identifier;
import ast.expr_def.Expression;
import ast.type.Type;
import compile.utils.IndentationKind;
import compile.utils.Indenter;

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

    @Override
    public void dump(int indent) {
        Indenter.indentWithShape(indent, IndentationKind.WhiteSpace);
        System.out.println(this.getClass().getSimpleName());
        // FIXME as long as JVM works
        if (initial != null)
            initial.dump(indent + 2);
    }
}
