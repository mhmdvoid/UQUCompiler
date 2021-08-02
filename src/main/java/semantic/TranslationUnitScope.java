package semantic;

import semantic.scope.TypeContext;

public class TranslationUnitScope extends Scope {
    TypeContext typeContext; // FIXME

    public TranslationUnitScope() {
        super(null, null);
        this.translationUnitScope = this;
        typeContext = new TypeContext(null);
    }

    @Override
    public TypeContext getTypeContext() {
        return typeContext;
    }
}
