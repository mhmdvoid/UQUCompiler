package ast.type;

import ast.type.Type;
import ast.type.TypeKind;

/**
 *
 */
public class Builtin extends Type {

    public Builtin(TypeKind kind, String name) {
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
