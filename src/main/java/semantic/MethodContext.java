package semantic;

// Todo
public class MethodContext extends LocalContext {
    public MethodContext(Context surroundingContext) {
        super(surroundingContext.translationUnitContext);
    }
}
