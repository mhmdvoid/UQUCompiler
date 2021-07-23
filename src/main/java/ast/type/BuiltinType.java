package ast.type;

public class BuiltinType extends Type {
    @Override
    public boolean equalType(Type type) {
        if (type instanceof BuiltinType) {
            var otherBuilt = (BuiltinType) type;
            return this.builtinContext == otherBuilt.builtinContext;
        }
        return false;
    }

    @Override
    public boolean binAccept() {
        return this.getKind() == TypeKind.BUILTIN_KIND && this.builtinContext == BuiltinContext.S_INT_32;
    }

    public enum BuiltinContext { // Fixme
        S_INT_32,
        BOOL_8,
        VOID_TYPE, // variables can never have it for now
        CHAR_8;
    }

    private BuiltinContext builtinContext;

    public BuiltinType(BuiltinContext typeContext) {
        super(TypeKind.BUILTIN_KIND, "builtin");
        builtinContext = typeContext;

    }

    @Override
    public String toString() {
        return "BuiltinType{" +
                "builtinContext=" + builtinContext +
                '}';
    }
}
