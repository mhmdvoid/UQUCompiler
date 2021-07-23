package ast.type;

public class TypeAliasKind extends Type{

    public TypeAliasKind(TypeKind kind) {
        super(kind);
    }

    @Override
    public boolean equalType(Type type) {
        return false;
    }

    @Override
    public boolean binAccept() {
        return false;
    }
}
