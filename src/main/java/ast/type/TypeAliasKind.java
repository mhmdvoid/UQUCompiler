package ast.type;

public class TypeAliasKind extends Type{

    public Type underlay;

    public TypeAliasKind(TypeKind kind, String name) {
        super(kind, name); // look it up here? need  acontex
    }

    @Override
    public boolean equalType(Type type) {
        return this.underlay.equalType(type);
    }

    @Override
    public boolean binAccept() {
        return false;
    }
}
