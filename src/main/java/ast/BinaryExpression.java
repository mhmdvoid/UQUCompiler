package ast;

import semantic.Context;

public class BinaryExpression extends Expression {

    Expression lhs;
    String operator;
    Expression rhs;

    public BinaryExpression(int line, Expression lhs, String operator, Expression rhs) {
        super(line);
        this.lhs = lhs;
        this.operator = operator;
        this.rhs = rhs;
    }

    @Override
    public String toString() {
        return "BinaryExpression{" +
                "lhs=" + lhs +
                ", operator='" + operator + '\'' +
                ", rhs=" + rhs +
                '}';
    }

    @Override
    public ASTNode semaAnalysis(Context context) {
        return typeChecker.typeBinExpression(this);

    }

    public Expression getLhs() {
        return lhs;
    }

    public String getOperator() {
        return operator;
    }

    public Expression getRhs() {
        return rhs;
    }

    //    Operator operator;
}

