package semantic;

public class MethodScope extends LocalScope {
    public MethodScope(Scope surroundingScope) {
        super(surroundingScope);
    }
}