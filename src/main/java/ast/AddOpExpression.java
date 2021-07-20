package ast;

import compile.utils.ShapeDump;

public class AddOpExpression extends BinaryExpression {

    public AddOpExpression(int line, Expression lhs, Expression rhs) {
        super(line, lhs, "+", rhs);
    }

    @Override
    public Expression typeCheck() {  // Todo: support more types specifically string concatenation. [MAYBE] ## Comparable in the future

        // Fixme: just to start that's a naive way to check
        if (lhs.type.kind != Type.BasicType.Int && rhs.type.kind != Type.BasicType.Int) {
            System.err.println("Types not compatible for add operation");
            type = new Type(lhs.type.kind);
        } else {

            type = new Type(Type.BasicType.Int);
        }


        // check if lhs.type.classof(type) then this whole expression will have that type
        /* —| e: int —| e:int
         ——————————————————————
            —| e + e : int
        */
        return this;
    }

    @Override
    protected void dump(int indent) {
        for (int i = 0; i < indent; i++) {
            ShapeDump.spaceTreeShape(ShapeDump.BasicShape.WhiteSpace);
        }
        System.out.println("BinaryAddOperation");
        lhs.dump(indent + 1);
        for (int i = 0; i < indent + 4; i++)
            ShapeDump.spaceTreeShape(ShapeDump.BasicShape.WhiteSpace);

        System.out.println("+");
        rhs.dump(indent + 1);
    }
}