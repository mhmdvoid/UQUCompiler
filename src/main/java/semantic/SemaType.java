package semantic;

import ast.nodes.Identifier;
import ast.type.Type;
import semantic.scope.type_scope.TypeContext;

// This assigns Type. Create type to be assigned by the parser.
public class SemaType extends SemaBase {
    public SemaType(Sema sema) {
        super(sema);
    }
    public Type resolveIntType() {
        return sema.astContext.int32Type;
    }
    public Type resolveBoolType() {
        return sema.astContext.bool8Type;
    }
}
