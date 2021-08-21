package semantic.redesign;

import ast.nodes.Identifier;
import ast.nodes.declaration.FuncDecl;
import ast.nodes.declaration.ParamDecl;
import ast.nodes.declaration.TypeAliasDecl;
import ast.nodes.declaration.VarDecl;
import ast.nodes.expression.Expr;
import ast.type.Type;
import ast.type.TypeKind;
import ast.type.UnresolvedType;

import java.util.List;

public class ScopeService {

    static ScopeService shared;
    private static class STATIC { public static final ScopeService service = new ScopeService(); }

    private ScopeService() {
        ADTScope.getInstance().enterScope();
    }

    public static ScopeService init() { return shared = STATIC.service; }

    public void nestedScope() {
        ADTScope.getInstance().enterScope(); // FIXME- Entering scope should be something really important, As we might get subtree; we need to exit it as well.
    }

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
            return decl;
        }
        System.err.println("Invalid redeclaration");
        return null;
    }

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


    // Deeply to check by type;
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
        return exist.getNameAliasType();
    }
}
