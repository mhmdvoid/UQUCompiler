package semantic;

// TODO
public class LocalContext extends Context {

    protected int offset;
    public LocalContext(Context surroundingContext) {
        // surro will be methodContext, surr.translation will be globalContext
        super(surroundingContext, surroundingContext.translationUnitContext);
        // We're deriving the value of the depth/offset from other scopes. Notice now implementation of nested blocks relates to AST and how deeply you walk it;
        this.offset = surroundingContext instanceof LocalContext ? ((LocalContext)surroundingContext).offset() : 0;
    }

    public int offset() {
        return this.offset;
    }

    public int nextOffset() {
        return this.offset++;
    }

}
