package semantic;

import ast.Identifier;
import ast.decl_def.*;
import ast.expr_def.Expression;
import ast.expr_def.FuncBlockExpr;
import ast.type.Type;
import ast.type.TypeKind;
import ast.type.UnresolvedType;
import semantic.scope.TypeContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SemaDecl extends SemaBase {
    //  UnresolvedTypes - This keeps track of all of the unresolved types in the AST.
    Map<Identifier, TypeAliasDecl> unresolvedTypes = new HashMap<>();
    List<TypeAliasDecl> unresolvedList = new ArrayList<>();

    public SemaDecl(Sema sema) {
        super(sema);
    }
    // kinda get it we handleEnd , with parser we do everything from scratch, once done call other phases to pass around and similar.
    public void handleEndOfTranslationUnit() {
        // So name binding is responsible for unresolved type, shadowing, import, libs and this sort of stuff. Sema is for now lifting all heavy work such as addToScope, lookup, create node, redefinition detect
        unresolvedTypes.forEach((identifier, nameAliasDeclNode) -> { // FIXME
            System.err.println("use of undeclared type " + identifier);;
        });
    }

    public TypeAliasDecl typeAliasSema(Identifier identifier, Type type, TypeContext ctx) {
        var def = ctx.lookup(identifier.name);
        if (def == null) {
            var typeAliasDecl = new TypeAliasDecl(type, identifier);
            ctx.addEntry(identifier.name, typeAliasDecl);
            return typeAliasDecl;
        }
        if (def.underlyingType.getKind() == TypeKind.UNRESOLVED_KIND) {
            def.underlyingType = type;
            unresolvedTypes.remove(identifier);
            return def;
        }
        System.err.println("SemaError redefinition: " + identifier + " already defined");
        return def;
    }

    public void addTo(Scope scope, ValueDecl valueDecl) {
        scope.addEntry(1, valueDecl.identifier.name, valueDecl);
    }

    public ValueDecl lookupValueName(Identifier identifier, Scope ctx) {  // when see var x: foo = 10;
        return ctx.lookup(identifier.name);
    }

    public TypeAliasDecl lookupTypename(Identifier identifier, TypeContext ctx) {
        var typeDef = ctx.lookup(identifier.name);
        if (typeDef != null) return typeDef;
        typeDef = new TypeAliasDecl(new UnresolvedType(TypeKind.UNRESOLVED_KIND, "unresolved"), identifier);
        ctx.addEntry(identifier.name, typeDef);
        unresolvedTypes.put(identifier, typeDef);
        unresolvedList.add(typeDef);
        return typeDef;

    }

    // Almost all have same sort of logic ! Well that's why we should move the name-binding logic somewhere. Looking up, Shadowing. This sort of things. And use polymorphism for ValueDecl nodes.
    public VarDecl varDeclSema(Identifier identifier, Type type, Expression init, Scope scope) {
        var valueDecl = scope.lookup(identifier.name);

        if (valueDecl != null) {
            System.err.println("Redefinition");return null;
        }
        // Null insert a new valueDecl to scope.
        valueDecl = new VarDecl(identifier, type, init);
        scope.addEntry(1, identifier.name, valueDecl);
        return (VarDecl) valueDecl;
    }

    public FuncDecl funcDeclSema(Type type, Identifier identifier,/*, FuncBlockExpr block,*/ Scope globalScope) {
        var funcDecl = globalScope.lookup(identifier.name);

        if (funcDecl != null) {
            System.err.println("Redefinition");return null;
        }
        // Null insert a new valueDecl to scope.
        funcDecl = new FuncDecl(identifier, type);
        globalScope.addEntry(1, identifier.name, funcDecl);
        return (FuncDecl) funcDecl;
    }

    public ParamDecl paramDeclSema(Identifier identifier, Type type, Scope local) {
        // Type should be lookup through the typeScope;
        // bound the name to a nested scope FIXME Should be pulled to nameBinder;
        var paramVal = local.lookup(identifier.name);
        if (paramVal != null) {
            System.err.println("Redefinition with same name in same scope");
            return null; // Fixme
        }
        var newParam = new ParamDecl(identifier, type);
        local.addEntry(0, identifier.name, newParam);
        return newParam;
    }
}
