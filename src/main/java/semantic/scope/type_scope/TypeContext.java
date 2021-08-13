package semantic.scope.type_scope;

import ast.decl_def.TypeAliasDecl;

import java.util.HashMap;
import java.util.Map;

// This should hold outermost scope for all types so can be visible for all. Global
public class TypeContext {

    TypeContext surroundingContext;

    public Map<String, TypeAliasDecl> typeScope;
    public TypeContext(TypeContext surroundingContext) {
        this.surroundingContext = surroundingContext;
        typeScope = new HashMap<>();
    }

    public void addEntry(String name, TypeAliasDecl decl) {
        if (typeScope.containsKey(name)) {
            System.err.println("Redefining name: " + name) ;
        } else {
            typeScope.put(name, decl);
        }
    }

    public TypeAliasDecl lookup(String name) {
        var definition = typeScope.get(name);
        return definition != null ? definition
                : surroundingContext != null ? surroundingContext.lookup(name)
                : null;
    }

    // We'll lookup the currentContext, and surronding. ex .uqulang : typealias Foo = int; func void s() {var x: Foo = 10; }; typeScope should be
    // localTypeContext. call lookup(foo) -> localTable -> null, surronding will be global will return back to us thankfully 1;

}
