package ast.decl_def;

import ast.Identifier;
import ast.expr_def.Expression;
import ast.type.Type;

// All AST nodes should be declared as private constructor !;
public class FuncDecl extends ValueDecl {

    public FuncDecl(Identifier identifier, Type type, Expression initial) {
        super(DeclKind.FuncDecl, identifier, type, initial);
    }
}
