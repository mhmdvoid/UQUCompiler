package semantic;

import ast.Identifier;
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

    public Type resolveTypename(Identifier identifier, TypeContext ctx) {
        return sema.decl.lookupTypename(identifier, ctx).getNameAliasType();
    }
}
