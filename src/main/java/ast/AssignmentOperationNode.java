package ast;

public class AssignmentOperationNode extends AssignmentStatement {

    public AssignmentOperationNode(Expression lhs, Expression rhs) {
        super(lhs, "=", rhs);
    }
}
