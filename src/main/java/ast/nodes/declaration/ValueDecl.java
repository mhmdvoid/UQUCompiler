package ast.nodes.declaration;

import ast.nodes.Identifier;
import ast.nodes.expression.Expr;
import ast.type.Type;
import compile.utils.IndentationKind;
import compile.utils.Indenter;

public class ValueDecl extends Decl{
    public Identifier identifier;
    public Type type;
    public Expr initial;

    public ValueDecl(DeclKind kind, Identifier identifier, Type type, Expr initial) {
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

    @Override
    public Expr getExpr() {
        return initial;
    }
}
