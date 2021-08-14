package semantic.scope;

import semantic.scope.type_scope.LocalTypeContext;
import semantic.scope.type_scope.TypeContext;

public class LocalScope extends Scope {
    public int offset;

    public LocalScope(Scope surroundingScope) {
        super(surroundingScope,surroundingScope.translationUnitScope);
        if (surroundingScope instanceof LocalScope)   // for nested block
            offset = ((LocalScope) surroundingScope).offset;
        else
            offset = 0;
    }

    @Override
    public TypeContext getTypeContext() {
        return new LocalTypeContext(surroundingScope.getTypeContext()); // Fixme: implement here LocalTypeContext;
    }

    public int nextOffset() { return offset++; }
}
