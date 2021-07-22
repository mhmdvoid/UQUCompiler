package semantic;

import ast.type.Type;

public class Definition {

    Type type;

    public Definition(Type type) {
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "Definition{" +
                "type=" + type +
                '}';
    }
}
