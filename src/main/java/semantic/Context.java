package semantic;

import java.util.HashMap;
import java.util.Map;

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
    public void addEntry(int line, String name, Definition definition) {
        if (table.containsKey(name)) {
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
