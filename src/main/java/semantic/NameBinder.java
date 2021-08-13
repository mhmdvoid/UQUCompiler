package semantic;

import ast.Identifier;
import ast.ASTInfo;
import ast.decl_def.*;
import ast.expr_def.Expression;
import ast.expr_def.ReferenceDeclExpr;
import ast.expr_def.UnresolvedReferenceExpr;
import ast.type.Type;
import ast.type.TypeKind;
import ast.type.UnresolvedType;
import lex_erro_log.ErrorLogger;
import lexer.SManagerSingleton;
import parser.Parser;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// For now varReference, import names.
//We may have unresolved type names as well.
/* A couple of notes:
    1- This doesn't perform type-checking && name-binding for the module's AST
    2- That means, For now will be used as implicit importing to the stdlib && runtime . And then resolve the Unresolved in the source-code 'Yours' for type-checking and access
    3- Supporting for one module's AST - See Module class 1-TU. Think of a module as many files or something. We may have a mapper/list to module -> *ASTs.

    However, this could change later. Honestly I don't want to add more complexity to a very simple language.
 */
public class NameBinder {
    public ASTInfo astInfo;
    List<Module> modules;
    Map<Identifier, ValueDecl> valueDecls;  // Value decls of `Yours` source code not other imported
    Map<ImportDecl, Module> imports;

    private NameBinder(ASTInfo astInfo) {
        this.astInfo = astInfo;
        modules = new ArrayList<>();
        valueDecls = new HashMap<>();
        imports = new HashMap<>();

    }

    public void addValueDecl(ValueDecl v) {
        valueDecls.put(v.identifier, v);
    }

    public void addImport(ImportDecl decl) {
        var mod = getModule(decl.name);
        if (mod != null)
            imports.put(decl, mod);
    }

    // We pass over the ast twice here
    public static void nameBinding(TranslationUnit tu, ASTInfo astInfo) {
        var binder = new NameBinder(astInfo); // Fixme;
        for (Decl decl : tu.getDecls()) {
            if (decl instanceof ValueDecl) {
                if (!((ValueDecl) decl).identifier.name.isEmpty())
                    binder.addValueDecl((ValueDecl) decl);
            }
            if (decl instanceof ImportDecl) {
                binder.addImport((ImportDecl) decl);
            }
        }

        // All source code valueDecl inserted we can check unresolvedExpr;
        for (Decl decl : tu.getDecls()) {
            if (decl instanceof ValueDecl) {
                if (((ValueDecl) decl).initial instanceof UnresolvedReferenceExpr)
                    ((ValueDecl) decl).initial = binder.bindNames(((ValueDecl) decl).initial, binder);  // Fixme
            }
        }

        // We before reporting an error should resolve them from imports. // We should do exactly the same?
        tu.unresolvedTypeList.forEach(unresolvedType -> {
            unresolvedType.underlyingType = binder.bindTypeDecl(unresolvedType.identifier); // Fixme : Replace
        });
    }

    private Module getModule(Identifier moduleName) {
        var name = moduleName.name + ".uqulang"; // Fixme: neither lexer nor parser check for filename extension as they load nothing into DB/Cache

        var file = new File(name);
        try {
            var fis = new FileInputStream(file);
            var tu = new Parser(file.getAbsolutePath(), new ASTInfo()).parseTranslateUnit();
            var mod = new Module(tu, moduleName.name);
            modules.add(mod);
            return mod;
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // FIXME
        }
    }

    Expression bindValueDeclName(Identifier identifier) {
        var s = valueDecls.get(identifier);
        if (s != null) {
            System.out.println("We found a resolved one in your source code no need to look through imports " + s.identifier);
            return new ReferenceDeclExpr(s);
        }

        // Not found in above valueDecls. Look for import module;
        for (Map.Entry<ImportDecl, Module> entry : imports.entrySet()) {
            ImportDecl importDecl = entry.getKey();
            Module module = entry.getValue();
            var v = module.lookupDecl(importDecl, identifier);
            if (v != null) {
                System.out.println("We found resolved identifier "+ v.identifier.name + " in module " + module.moduleName);
                return new ReferenceDeclExpr(v);
            }
        }
        ErrorLogger.log(SManagerSingleton.shared().srcCode(), identifier.location.column, identifier.location.newColumn());
        System.out.println("use of unresolved identifier " + identifier.name);
        return new UnresolvedReferenceExpr(identifier);

    }

    // Fixme: Detect circular redefinition. WE NOT SUPPORTING NAMESPACE
    Type bindTypeDecl(Identifier identifier) {
        // Since SemaDecl resolves typeDecl in the whole source code,
        // So here we'll have unresolved types because %100 don't appear in `Yours` source code
        // So for now we just try to look them up in the import module, if found return ResolvedTypeAlias;
        for (Map.Entry<ImportDecl, Module> entry : imports.entrySet()) {
            ImportDecl importDecl = entry.getKey();
            Module module = entry.getValue();
            var taDecl = module.lookupTypename(identifier);
            if (taDecl != null) {  // We should removed tu.unresolvedTypes ,clear them!;
                System.out.println("Found resolved type in other module " + taDecl.identifier.name);
                return taDecl.underlyingType;
            }
        }
        ErrorLogger.log(SManagerSingleton.shared().srcCode(), identifier.location.column, identifier.location.newColumn());
        System.err.println("Not found in module as well");
        return new UnresolvedType(TypeKind.UNRESOLVED_KIND, identifier.name);
    }

    Expression bindNames(Expression expression, NameBinder binder) {
        var unresolvedExpr = (UnresolvedReferenceExpr) expression;
        return binder.bindValueDeclName(unresolvedExpr.identifier);
    }
}