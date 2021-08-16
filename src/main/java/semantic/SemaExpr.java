package semantic;

import ast.nodes.Identifier;
import ast.nodes.expression.Expr;
import ast.nodes.expression.IntegerLiteral;
import ast.nodes.expression.ReferenceDeclExpr;
import ast.nodes.expression.UnresolvedReferenceExpr;
import semantic.scope.Scope;

public class SemaExpr extends SemaBase {
    public SemaExpr(Sema sema) {
        super(sema);
    }

    public IntegerLiteral semaNumberConstant(String textValue) {

        // this is the sema part. assigning type, lookup, create node, redefinition error and similar;
        /* 1- We check integer fits in 32-bits for mips otherwise compile-error value too large;
           2- assign to it a type 'built-in type'
           3- create a new node
         */
        return new IntegerLiteral(textValue);
    }

    public Expr semaIdentifierRef(Identifier identifier, Scope scope) {
        var valueDecl = sema.decl.lookupValueName(identifier, scope);
        // Lookup;
        // if (null) unresolvedRefExpression(identifier.name);
        if (valueDecl == null) {
//            System.out.println("Unresolved " + identifier.name + " til now. Check NameBinder");
            return new UnresolvedReferenceExpr(identifier);
        }
        return new ReferenceDeclExpr(valueDecl);
    }
}
