package semantic.redesign;

import ast.Expression;
import ast.NameAliasDecl;
import ast.redesign.Identifier;
import ast.redesign.NameAliasDeclNode;
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
    public NameAliasDeclNode tpAliasSema(/*Identifier*/ String name, Type type, Scope ctx) {  // when see typealais decl
        var def = ctx.lookup(name);

        if (def == null) {
            var newAliasDelc = new NameAliasDeclNode(type, name);
            ctx.addEntry(0, name, newAliasDelc);
            return newAliasDelc;
        }
        // else means redefinition?
        System.err.println("SemaError redefinition: " + name + " already defined");
        return def;
        // Look it up here and then check if not exists
        // push entry to scope so then we look it up !;
    }

    public NameAliasDeclNode lookupTypename(Identifier identifier, Scope ctx) {  // when see var x: foo = 10;
        var typeDef =  ctx.lookup(identifier.name);
        if (typeDef != null)
            return typeDef;


        // null? means create a new one with unresolved type
        typeDef = new NameAliasDeclNode(new UnresolvedType(TypeKind.UNRESOLVED_KIND, "unresolved"), identifier.name);
        ctx.addEntry(0, identifier.name, typeDef);
        return typeDef;
    }

    public VarDecl varDeclSema(Identifier identifier, Type type, Expression init, Scope scope) {
        // scope.lookup(identifer.name) if not null redefine !;
        // create it and add to currentScope/ the one being sent.
        // then return it;
        return null;
    }
}
