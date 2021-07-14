package ast;

public abstract class AssignmentStatement extends BinaryExpression {

    public AssignmentStatement(Expression lhs, String operator, Expression rhs) {
        super(lhs, operator, rhs);
        isStatementExpression = true;
    }
}
