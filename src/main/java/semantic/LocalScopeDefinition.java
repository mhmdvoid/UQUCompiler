package semantic;

import ast.type.Type;

public class LocalScopeDefinition extends Definition {
    private int offset;
    private boolean isInitialized;

    public LocalScopeDefinition(Type type, int offset) {
        super(type);
        this.offset = offset;
    }

    public Type type() {
        return this.type;
    }

    public int offset() {
        return this.offset;
    }

    public void initialize() {
        this.isInitialized = true;
    }

    public boolean isInitialized() {
        return this.isInitialized;
    }
}
