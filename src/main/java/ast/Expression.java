package ast;

// This represent expression 'No side effect ' except for statementExpression?
public abstract class Expression extends Statement {

	public Type type;

    protected boolean isStatementExpression;

    protected Expression(int line) {
        super(line);
    }

    public abstract Expression typeCheck();
}

