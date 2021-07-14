package ast;


class IntegerLiteral extends Expression {
	

	String intValue;


	public IntegerLiteral(String intValue) 
	{
		this.intValue = intValue;
	}

	public Expression typeCheck() {
		this.type = new Type(Type.BasicType.Int);
		return this;
	}
}