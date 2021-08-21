package semantic.redesign;

import ast.nodes.Identifier;
import ast.nodes.declaration.*;
import ast.nodes.expression.Expr;
import ast.nodes.expression.ReferenceDeclExpr;
import ast.nodes.expression.UnresolvedReferenceExpr;
import ast.type.Type;
import ast.type.TypeKind;
import ast.type.UnresolvedType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScopeService {

    static ScopeService shared;
    private final List<TypeAliasDecl> unresolvedTypeList;
    private final Map<Identifier, TypeAliasDecl> unresolvedMap;
    private static class STATIC { public static final ScopeService service = new ScopeService(); }

    private ScopeService() {
        ADTScope.getInstance().enterScope();
        unresolvedTypeList = new ArrayList<>();
        unresolvedMap = new HashMap<>();
    }

    public static ScopeService init() { return shared = STATIC.service; }

    // Helpers
    public void newScope() {
        ADTScope.getInstance().enterScope(); // FIXME- Entering scope should be something really important, As we might get subtree; we need to exit it as well.
    }

    public void exitScope() {
        ADTScope.getInstance().exitScope();
    }

    public void handleEndOfTranslationUnit(TranslationUnit tu) {
        unresolvedTypeList.removeIf(typeAliasDecl -> typeAliasDecl.underlyingType.getKind() != TypeKind.UNRESOLVED_KIND);
        tu.unresolvedTypeList.addAll(unresolvedTypeList);
    }

    //----------------------------------------------------------------------//
    //                              Decls   `                               //
    //----------------------------------------------------------------------//

    public VarDecl varDeclScope(Identifier identifier, Type type, Expr init) {
        var decl = ADTScope.getInstance().lookupCurrent(identifier);
        if (decl != null) {
            System.err.println("Error redefinition");
            return (VarDecl) decl;
        }
        decl = new VarDecl(identifier, type, init);
        ADTScope.getInstance().addValueDecl(identifier, decl);
        return (VarDecl) decl;
    }

    public TypeAliasDecl typeAliasDeclScope(Type type, Identifier identifier) {
        var decl = ADTScope.getInstance().lookupTypeParent(identifier);
        if (decl == null /*|| adt.currentLevel != depthFoundIn*/) {
            // if (decl != null && type unresolved) {  found and for sure you can't find decl in the same level. so found in anohter level
                    // type unresolved is double check of course; and means was injected to outermost;
            //  fix it and make the typedecl.type = toTyope
            // }


            decl = new TypeAliasDecl(type, identifier);
            ADTScope.getInstance().addTypeDecl(identifier, decl);
            return decl;
        }

        if (decl.underlyingType.getKind() == TypeKind.UNRESOLVED_KIND) {
            decl.underlyingType = type;
            unresolvedMap.remove(identifier);
            return decl;
        }
        System.err.println("Invalid redeclaration");
        return null;
    }

    // FIXME: this when get called will be in ParamDecl as current scope
    public FuncDecl funcDeclScope(Identifier identifier, Type type, List<ParamDecl> paramDecls) {

        var fn = ADTScope.getInstance().lookupParent(identifier); // current scope already
        if (fn != null) {
            System.err.println("Invalid redeclaration");
            return null;
        }
        fn = new FuncDecl(identifier, type, paramDecls);
        ADTScope.getInstance().addValueDecl(identifier, fn);
        return (FuncDecl) fn;
    }

    public ParamDecl paramDeclScope(Identifier identifier, Type type) {
        var param = ADTScope.getInstance().lookupCurrent(identifier);
        if (param != null) {
            System.err.println("Invalid redclaration");
            return null;
        }
        param = new ParamDecl(identifier, type, 0 /*FIXME- paramOffset*/);
        ADTScope.getInstance().addValueDecl(identifier, param);
        return (ParamDecl) param;
    }

    public FuncDecl funcBodyScope(FuncDecl funcDecl, Expr block) {
        // assert funcDecl and body not null;
        funcDecl.initial = block;
        return funcDecl;
    }

    public ImportDecl importDeclSema(Identifier module) {
        return new ImportDecl(module);
    }

    //----------------------------------------------------------------------//
    //                              Lookups                                 //
    //----------------------------------------------------------------------//
    public Type lookupUseType(Identifier identifier) {
        var exist = ADTScope.getInstance().lookupTypeParent(identifier);
        if (exist != null) {
            // Forward: decl was found before use;
            return exist.getNameAliasType();
        }
        // inject it in outermost;
        exist = new TypeAliasDecl(new UnresolvedType(TypeKind.UNRESOLVED_KIND, "unresolved"), identifier);
        var outermost = ADTScope.getInstance().outermost();
        ADTScope.getInstance().insertInto(outermost, identifier, exist);
        unresolvedTypeList.add(exist);
        unresolvedMap.put(identifier, exist);
        return exist.getNameAliasType();
    }
    public Expr lookupValueName(Identifier identifier) {
        var value = ADTScope.getInstance().lookupParent(identifier);
        if (value == null) {
            System.err.println("Ignore it: Unresolved Reference " + identifier + " later phases to resolve it");
            // notice this objects gets called while parsing having the ability to know little info can beStored;
            // TODO: Store this little info, unresolvedReferences.push(x);
            return new UnresolvedReferenceExpr(identifier);
        }
        return new ReferenceDeclExpr(value);

    }
}
