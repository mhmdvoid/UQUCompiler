package ast;

import ast.type.BuiltinType;
import compile.utils.ShapeDump;
import semantic.Context;

public class BoolLiteral extends Expression {
    String value;
    public BoolLiteral(int line, String value) {
        super(line);
        this.value = value;
//        typeChecker.typeConstantValue(this, BuiltinType.BuiltinContext.BOOL_8);

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
