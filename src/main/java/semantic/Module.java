package semantic;

import ast.Identifier;
import ast.decl_def.*;

import java.util.HashMap;
import java.util.Map;

public class Module {
    // For AST manipulation, lookup, etc.
    TranslationUnit translationUnit;  // Store source code of the module inhere.
    Map<Identifier, ValueDecl> decls;
    public String moduleName;
    public Module(TranslationUnit translationUnit, String moduleName) {
        this.translationUnit = translationUnit;
        decls = new HashMap<>();
        this.moduleName = moduleName;
    }
    public ValueDecl lookupDecl(ImportDecl importDecl, Identifier identifier) {
        if (decls.isEmpty()) {
            for (Decl decl : translationUnit.getDecls()) {
                if (decl instanceof ValueDecl) {
                    if (!((ValueDecl) decl).identifier.name.isEmpty()) {
                        this.decls.put(((ValueDecl) decl).identifier, (ValueDecl) decl);
                    }
                }
            }
        }
        return this.decls.get(identifier);

    }

    // If we have classDecl, structDecl, then we may send a scope to look in;
    public TypeAliasDecl lookupTypename(Identifier identifier) {
        var t = translationUnit.tuScope.getTypeContext().lookup(identifier.name);
        return t; // Could be null;
    }
}
