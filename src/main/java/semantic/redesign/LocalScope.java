package semantic.redesign;

public class LocalScope extends Scope {
    public LocalScope(Scope surroundingScope) {
        super(surroundingScope,surroundingScope.translationUnitScope);
    }
}
