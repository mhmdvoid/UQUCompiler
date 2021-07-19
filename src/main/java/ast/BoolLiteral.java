package ast;

public class BoolLiteral extends Expression {
    String value;
    public BoolLiteral(int line, String value) {
        super(line);
        this.value = value;
    }

    public Expression typeCheck() {
        type = new Type(Type.BasicType.Bool);
        return this;
    }

    @Override
    public String toString() {
        return "BoolLiteral{" +
                "value='" + value + '\'' +
                '}';
    }
}
