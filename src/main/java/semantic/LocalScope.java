package semantic;

public class LocalScope extends Scope {
    public LocalScope(Scope surroundingScope) {
        super(surroundingScope,surroundingScope.translationUnitScope);
    }
}
