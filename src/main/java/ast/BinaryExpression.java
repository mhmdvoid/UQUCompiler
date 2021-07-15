package ast;
enum Operator {
    ADD,
    SUB,
    ASSIGN,
    EQUAL,
}
public class BinaryExpression extends Expression {

    Expression lhs;
    String operator;
    Expression rhs;

    public BinaryExpression(Expression lhs, String operator, Expression rhs) {
        this.lhs = lhs;
        this.operator = operator;
        this.rhs = rhs;
    }

    @Override
    public String toString() {
        return "BinaryExpression{" +
                "lhs=" + lhs +
                " rhs=" + rhs +
                '}';
    }

    //    Operator operator;
}

