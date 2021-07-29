package semantic.redesign;

import ast.redesign.Identifier;
import ast.type.Type;

// This assigns Type. Create type to be assigned by the parser.
public class SemaType extends SemaBase {
    public SemaType(Sema sema) {
        super(sema);
    }
    public Type resolveIntType() {
        return sema.astContext.int32Type;
    }

    public Type resolveTypename(/*Type result*, Loc loc*/ Identifier identifier) {

        return sema.decl.lookupTypename(identifier).getUnderlyingType();
        // Look it up!;
    }
}
