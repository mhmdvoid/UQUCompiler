package semantic.redesign;

import ast.redesign.TypeAliasDecl2;

import java.util.HashMap;
import java.util.Map;

public class Scope {
    Scope surroundingScope;

    TranslationUnitScope translationUnitScope;

    public Map<String, TypeAliasDecl2> table; // node

    public Scope(Scope surroundingScope, TranslationUnitScope translationUnitScope) {
        this.surroundingScope = surroundingScope;
        this.translationUnitScope = translationUnitScope;
        table = new HashMap<>();
    }
    // Todo: include line with def type for better error message;
    public void addEntry(int line, String name, TypeAliasDecl2 node) {
        if (table.containsKey(name)) {
            System.err.println("Redefining name: " + name + " line: " + line) ;
        } else {
            table.put(name, node);
        }
    }

    public TypeAliasDecl2 lookup(String name) {
        var definition = table.get(name);
        return definition != null ? definition
                : surroundingScope != null ? surroundingScope.lookup(name)
                : null;
    }
}
