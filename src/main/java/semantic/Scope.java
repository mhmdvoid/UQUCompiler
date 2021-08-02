package semantic;

import ast.decl_def.TypeAliasDecl;

import java.util.HashMap;  // Scope data structure 'Stack/List' of hashtable.  [global{foo(): int}, methodScope{param: bool}, blockScope{c: char}]; we have a depth to distinguish levels.
// 1 -> we're on global scope. 2 we're methodScope., 3 Local. and number of depth increases accordingly. We can put on constraints to limit the depth but that's ok alright?
import java.util.Map;

public class Scope {
    Scope surroundingScope;

    TranslationUnitScope translationUnitScope;

    public Map<String, TypeAliasDecl> table; // node

    public Scope(Scope surroundingScope, TranslationUnitScope translationUnitScope) {
        this.surroundingScope = surroundingScope;
        this.translationUnitScope = translationUnitScope;
        table = new HashMap<>();
    }
    // Todo: include line with def type for better error message;
    public void addEntry(int line, String name, TypeAliasDecl node) {
        if (table.containsKey(name)) {
            System.err.println("Redefining name: " + name + " line: " + line) ;
        } else {
            table.put(name, node);
        }
    }

    public TypeAliasDecl lookup(String name) {
        var definition = table.get(name);
        return definition != null ? definition
                : surroundingScope != null ? surroundingScope.lookup(name)
                : null;
    }
}
