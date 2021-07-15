package ast;

public class AssignmentOperationNode extends AssignmentStatement {

    public AssignmentOperationNode(Expression lhs, Expression rhs) {
        super(lhs, "=", rhs);
    }

    @Override
    public String toString() {
        return "AssignmentOperationNode{" +
                "lhs=" + lhs  +
                " rhs=" + rhs +
                '}';
    }
}
