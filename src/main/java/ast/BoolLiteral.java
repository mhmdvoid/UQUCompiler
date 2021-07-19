package ast;

public class BoolLiteral extends Expression {
    String value;
    public BoolLiteral(int line, String value) {
        super(line);
        this.value = value;
    }

    @Override
    public String toString() {
        return "BoolLiteral{" +
                "value='" + value + '\'' +
                '}';
    }
}
