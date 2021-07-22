package ast;

public class BinaryExpression extends Expression {

    Expression lhs;
    String operator;
    Expression rhs;

    public BinaryExpression(int line, Expression lhs, String operator, Expression rhs) {
        super(line);
        this.lhs = lhs;
        this.operator = operator;
        this.rhs = rhs;
        typeChecker.typeBinExpression(this);
    }


    @Override
    public String toString() {
        return "BinaryExpression{" +
                "lhs=" + lhs +
                ", operator='" + operator + '\'' +
                ", rhs=" + rhs +
                '}';
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

