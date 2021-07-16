package ast;

public class AssignmentOperationNode extends AssignmentStatement {

    public AssignmentOperationNode(int line, Expression lhs, Expression rhs) {
        super(line, lhs, "=", rhs);
    }

    @Override
    public String toString() {
        return "AssignmentOperationNode{" +
                "lhs=" + lhs  +
                " rhs=" + rhs +
                " line=" + getLine() +
                '}';
    }
}
