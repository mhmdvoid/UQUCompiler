package semantic.redesign;

import ast.Expression;
import ast.redesign.Identifier;
import ast.redesign.TypeAliasDecl2;
import ast.VarDecl;
import ast.type.Type;
import ast.type.TypeKind;
import ast.type.redesign.UnresolvedType;


// NodeDecl creation
public class SemaDecl extends SemaBase {
    Scope scope = new TranslationUnitScope(); // creating it ;
    public SemaDecl(Sema sema) {
        super(sema);
    }

    public TypeAliasDecl2 tpAliasSema(/*Identifier*/ String name, Type type) {  // when see typealais decl

        // Look it up here and then check if not exists
        // push entry to scope so then we look it up !;
        return new TypeAliasDecl2(type, name);
    }

    public TypeAliasDecl2 lookupTypename(Identifier identifier) {  // when see var x: foo = 10;
        var typeDef = scope.lookup(identifier.name);
        if (typeDef != null)
            return typeDef;


        // null? means create a new one with unresolved type
        typeDef = new TypeAliasDecl2(new UnresolvedType(TypeKind.UNRESOLVED_KIND, "unresolved"), identifier.name);
        scope.addEntry(0, identifier.name, typeDef);
        return typeDef;
    }

    public VarDecl varDeclSema(Identifier identifier, Type type, Expression init, Scope scope) {
        // scope.lookup(identifer.name) if not null redefine !;
        // create it and add to currentScope/ the one being sent.
        // then return it;
        return null;
    }
}
