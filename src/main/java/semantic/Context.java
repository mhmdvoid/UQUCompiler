package semantic;

import ast.ASTNode;

import java.util.HashMap;
import java.util.Map;

// Fixme: Rename to Scope
public class Context {
     Context surroundingContext;

     TranslationUnitContext translationUnitContext;

     public Map<String,Definition> table;


    public Context(Context surroundingContext, TranslationUnitContext translationUnitContext) {
        this.surroundingContext = surroundingContext;
        this.translationUnitContext = translationUnitContext;
        table = new HashMap<>();
    }
    // Todo: include line with def type for better error message;
    public void addEntry(int line, String name, Definition definition, ASTNode node) {
        if (table.containsKey(name)) {
             node.semaError = true;  // Fixme: this is the trivial way later we'll have better OO design
            System.err.println("Redefining name: " + name + " line: " + line) ;
        } else {
            table.put(name, definition);
        }
    }

    public Definition lookup(String name) {
        var definition = table.get(name);
        return definition != null ? definition
                : surroundingContext != null ? surroundingContext.lookup(name)
                : null;
    }
}
