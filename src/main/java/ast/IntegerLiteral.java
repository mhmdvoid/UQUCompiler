package ast;


public class IntegerLiteral extends Expression {
	

	String intValue;


	public IntegerLiteral(String intValue) 
	{
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
				'}';
	}
}