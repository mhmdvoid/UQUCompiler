package semantic;

import semantic.scope.TypeContext;

public class LocalScope extends Scope {
    public LocalScope(Scope surroundingScope) {
        super(surroundingScope,surroundingScope.translationUnitScope);
    }

    @Override
    public TypeContext getTypeContext() {
        return null; // Fixme: implement here LocalTypeContext;
    }
}
