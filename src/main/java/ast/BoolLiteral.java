package ast;

import compile.utils.ShapeDump;

public class BoolLiteral extends Expression {
    String value;
    public BoolLiteral(int line, String value) {
        super(line);
        this.value = value;
    }

    public Expression typeCheck() {
        type = new Type(Type.BasicType.Bool);
        return this;
    }

    @Override
    public String toString() {
        return "BoolLiteral{" +
                "value='" + value + '\'' +
                '}';
    }

    @Override
    protected void dump(int indent) {
        for (int i = 0; i < indent; i++) {
            ShapeDump.spaceTreeShape(ShapeDump.BasicShape.WhiteSpace);     // shape.print(-) in a separate class;
        }
        System.out.println("BoolLiteral " + value);
    }
}
