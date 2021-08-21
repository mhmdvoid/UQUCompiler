package semantic.redesign;

import ast.nodes.Identifier;
import ast.nodes.declaration.ValueDecl;
import java.util.HashMap;
import java.util.Map;

class ValueHT extends HashMap<Identifier, ValueDecl> {
    public ValueHT(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public ValueHT(int initialCapacity) {
        super(initialCapacity);
    }

    public ValueHT() {
    }

    public ValueHT(Map<? extends Identifier, ? extends ValueDecl> m) {
        super(m);
    }
}
