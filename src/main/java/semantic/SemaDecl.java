package semantic;

import ast.Identifier;
import ast.decl_def.*;
import ast.expr_def.Expression;
import ast.type.Type;
import ast.type.TypeKind;
import ast.type.UnresolvedType;
import lex_erro_log.ErrorLogger;
import lexer.SManagerSingleton;
import semantic.scope.FuncScope;
import semantic.scope.Scope;
import semantic.scope.type_scope.TypeContext;

import java.util.*;

public class SemaDecl extends SemaBase {
    //  UnresolvedTypes - This keeps track of all of the unresolved types in the AST.
    Map<Identifier, TypeAliasDecl> unresolvedTypes = new HashMap<>();
    List<TypeAliasDecl> unresolvedTypeList = new ArrayList<>();

    public SemaDecl(Sema sema) {
        super(sema);
    }
    // kinda get it we handleEnd , with parser we do everything from scratch, once done call other phases to pass around and similar.
    public void handleEndOfTranslationUnit(TranslationUnit tu) {
        // Move unresolvedTypes to TU so we can access them in NameBinding
        unresolvedTypeList.removeIf(aliasDecl -> aliasDecl.underlyingType.getKind() != TypeKind.UNRESOLVED_KIND);
        tu.unresolvedTypeList.addAll(unresolvedTypeList);
    }

    public TypeAliasDecl typeAliasSema(/*Position loc*/ Identifier identifier, Type type, TypeContext ctx) {
        var def = ctx.lookup(identifier.name);
        if (def == null) {
            var typeAliasDecl = new TypeAliasDecl(type, identifier);
//            typeAliasDecl.location = loc;
            ctx.addEntry(identifier.name, typeAliasDecl);
            return typeAliasDecl;
        }
        if (def.underlyingType.getKind() == TypeKind.UNRESOLVED_KIND) {
            def.underlyingType = type;
            unresolvedTypes.remove(identifier);
            return def;
        }

        System.out.println("invalid redeclaration: " + identifier.location);

        ErrorLogger.log(SManagerSingleton.shared().srcCode(), identifier.location.column, identifier.location.newColumn());

        System.out.println("Note; previous declaration here " + def.identifier.location);
        ErrorLogger.log(SManagerSingleton.shared().srcCode(), def.identifier.location.column, def.identifier.location.newColumn());
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
        unresolvedTypeList.add(typeDef);
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

    public ParamDecl paramDeclSema(Identifier identifier, Type type, FuncScope local) {
        // bound the name to a nested scope FIXME Should be pulled to nameBinder;
        var newParam = new ParamDecl(identifier, type, local.nextOffset());
        local.addEntry(0, identifier.name, newParam);
        return newParam;
    }

    public ImportDecl importDeclSema(Identifier module) {
        return new ImportDecl(module);
    }
}
