package semantic;

// TODO
public class LocalContext extends Context {
    public LocalContext(Context surroundingContext) {
        // surro will be methodContext, surr.translation will be globalContext
        super(surroundingContext, surroundingContext.translationUnitContext);
    }
}
