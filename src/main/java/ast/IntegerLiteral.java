package ast;


public class IntegerLiteral extends Expression {
	

	String intValue;


	public IntegerLiteral(int line, String intValue)
	{
		super(line);
		this.intValue = intValue;
		typeCheck();
	}

	public Expression typeCheck() {
		this.type = new Type(Type.BasicType.Int);
		return this;
	}

	@Override
	public String toString() {
		return "IntegerLiteral{" +
				"type=" + type +
				", intValue='" + intValue + '\'' +
				" line=" + getLine() +
				'}';
	}
}