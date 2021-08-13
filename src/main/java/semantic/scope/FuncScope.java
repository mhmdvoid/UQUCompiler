package semantic.scope;

public class FuncScope extends LocalScope {
    public FuncScope(Scope surroundingScope) {
        super(surroundingScope);
        offset = 0;
    }
}
