package ast;

import compile.utils.ShapeDump;

public class MulOpExpression extends BinaryExpression{
    public MulOpExpression(int line, Expression lhs, Expression rhs) {
        super(line, lhs, "*", rhs);
    }
    @Override
    public Expression typeCheck() {  // Todo: support more types specifically string concatenation. [MAYBE] ## Comparable in the future
        if (lhs.type.kind != Type.BasicType.Int && rhs.type.kind != Type.BasicType.Int) {
            System.err.println("Types not compatible for mul operation");
        }

        type = new Type(Type.BasicType.Int);
        return this;
    }

    @Override
    protected void dump(int indent) {
        for (int i = 0; i < indent; i++) {
            ShapeDump.spaceTreeShape(ShapeDump.BasicShape.WhiteSpace);
        }
        System.out.println("BinaryMulOperation");
        lhs.dump(indent + 1);

        for (int i = 0; i < indent + 4; i++)
            ShapeDump.spaceTreeShape(ShapeDump.BasicShape.WhiteSpace);

        System.out.println("*");
        rhs.dump(indent + 1);
    }
}
