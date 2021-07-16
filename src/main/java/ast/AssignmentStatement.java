package ast;

public abstract class AssignmentStatement extends BinaryExpression {

    public AssignmentStatement(int line, Expression lhs, String operator, Expression rhs) {
        super(line, lhs, operator, rhs);
        isStatementExpression = true;
    }
}
