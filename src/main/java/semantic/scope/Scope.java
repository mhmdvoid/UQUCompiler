package semantic.scope;

import ast.decl_def.ValueDecl;
import semantic.scope.type_scope.TypeContext;

import java.util.HashMap;
import java.util.Map;

public abstract class Scope {
    public Scope surroundingScope;

    public TranslationUnitScope translationUnitScope;

    public Map<String, ValueDecl> table; // node

    public Scope(Scope surroundingScope, TranslationUnitScope translationUnitScope) {
        this.surroundingScope = surroundingScope;
        this.translationUnitScope = translationUnitScope;
        table = new HashMap<>();
    }
    // Todo: include line with def type for better error message;
    public void addEntry(int line, String name, ValueDecl valueDecl) {
        if (table.containsKey(name)) {
            System.err.println("Redefining name: " + name + " line: " + line) ;
        } else {
            table.put(name, valueDecl);
        }
    }

    public ValueDecl lookup(String name) {
        var definition = table.get(name);
        return definition != null ? definition
                : surroundingScope != null ? surroundingScope.lookup(name)
                : null;
    }
    public abstract TypeContext getTypeContext() ;
}
