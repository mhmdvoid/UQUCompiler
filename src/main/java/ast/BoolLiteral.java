package ast;

import compile.utils.ShapeDump;

public class BoolLiteral extends Expression {
    String value;
    public BoolLiteral(int line, String value) {
        super(line);
        this.value = value;

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
