package semantic.redesign;

import ast.nodes.Identifier;
import ast.nodes.declaration.TypeAliasDecl;
import ast.nodes.declaration.ValueDecl;
import java.util.Stack;

public class ADTScope {


    private int parent = -1;
    public int level = -1;  // like the current scope
    Stack<PairScope> scopeStack = new Stack<>();
    private static ADTScope shared;

    private ADTScope() {}

    public static class PairScope {
        ValueHT valueScope;
        TypeHT typeScope;

        public PairScope() {
            valueScope = new ValueHT();
            typeScope = new TypeHT();
        }
        public ValueHT first() {return valueScope; }
        public TypeHT second() {return typeScope; }
    }

    private static class Single { public static final ADTScope instance = new ADTScope(); }

    public static ADTScope getInstance() {
        return shared = Single.instance;
    }

    public void enterScope() {
        parent = ++level;
        scopeStack.push(new PairScope());
    }

    public void exitScope() {
        parent = --level;
        scopeStack.pop();
    }

    public PairScope currentScope() {
        return scopeStack.peek();
    }

    public void addTypeDecl(Identifier identifier, TypeAliasDecl typeAliasDecl) {
        currentScope().second().put(identifier, typeAliasDecl);
    }

    public void addValueDecl(Identifier identifier, ValueDecl valueDecl) {
        currentScope().first().put(identifier, valueDecl);
    }

    public TypeAliasDecl lookupType(Identifier identifier) {
        return currentScope().second().get(identifier);
    }

    public ValueDecl lookupCurrent(Identifier identifier) {
        return currentScope().first().get(identifier);
    }

    public TypeAliasDecl lookupTypeParent(Identifier identifier) {

        // TODO: return found depth. So clients can access depth & level;
        for (int i = level; i >= 0; i--) {
            var p = scopeStack.get(i).second().get(identifier);
            if (p != null) {
                // Redefinition: Decl exists && found in same level.
                if (i != level) return null;  // This way get rid of lookupCurrentType
                // as we don't need it anymore. TODO: replace lookupCurrent with level/depth encapsulation
                return p;
            }
        }
        return null;
    }

    public ValueDecl lookupParent(Identifier identifier) {
        // Linear - 1?
        for (int i = level; i >= 0; i--) {
            var p = scopeStack.get(i).first().get(identifier);
            if (p != null)
                return p;
        }
        return null;
    }

    // FIXME we should do another client-friendly implementation to get the parent of a currentScope;
    // This return the TUScope which is the most outer scope we have
    public TypeHT outermost() {
        return scopeStack.get(0).second();
    }

    // FIXME
    public void insertInto(TypeHT typeScope, Identifier identifier, TypeAliasDecl decl) {
        typeScope.put(identifier, decl);
    }

    public TypeHT getParent() {
        if (parent < 0 || parent == 0) return null;
        return scopeStack.get(--parent).second();
    }
}