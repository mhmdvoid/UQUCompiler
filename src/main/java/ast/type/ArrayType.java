package ast.type;

public class ArrayType extends Type {
    private final Type base;
    private final int size;   // for now can't be zero, and checked by the parser;

    public ArrayType(Type base, int size) {
        super(TypeKind.ARRAY_KIND, "Array");
        this.base = base;
        this.size = size;
    }

    public Type getBase() {
        return base;
    }

    public int getSize() {
        return size;
    }


    static boolean typeof(ArrayType type) {
        return true;
    }

    static boolean typeof(Type type) {
        return type.getKind() == TypeKind.ARRAY_KIND;
    }

    @Override
    public boolean equalType(Type type) {
        return false;  // TODO: 7/22/21  
    }

    @Override
    public boolean binAccept() {
        return false;
    }
}
