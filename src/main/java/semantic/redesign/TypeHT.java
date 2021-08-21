package semantic.redesign;

import ast.nodes.Identifier;
import ast.nodes.declaration.TypeAliasDecl;

import java.util.HashMap;
import java.util.Map;

class TypeHT extends HashMap<Identifier, TypeAliasDecl> {
    public TypeHT(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public TypeHT(int initialCapacity) {
        super(initialCapacity);
    }

    public TypeHT() {
    }

    public TypeHT(Map<? extends Identifier, ? extends TypeAliasDecl> m) {
        super(m);
    }
}
