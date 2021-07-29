package ast.type.redesign;

import ast.type.Type;
import ast.type.TypeKind;

public class UnresolvedType extends Type {

    public UnresolvedType(TypeKind kind, String name) {
        super(kind, name);
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
