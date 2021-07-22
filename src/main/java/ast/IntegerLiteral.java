package ast;


import ast.type.BuiltinType;
import compile.utils.ShapeDump;

public class IntegerLiteral extends Expression {
	

	String intValue;


	public IntegerLiteral(int line, String intValue) {

		super(line);
		this.intValue = intValue;
		typeChecker.typeConstantValue(this, BuiltinType.BuiltinContext.S_INT_32);  // a better approach to traverse !;
	}


	@Override
	public String toString() {
		return "IntegerLiteral{" +
				"type=" + type +
				", intValue='" + intValue + '\'' +
				" line=" + getLine() +
				'}';
	}

	@Override
	protected void dump(int indent) {
		for (int i = 0; i < indent; i++) {
			ShapeDump.spaceTreeShape(ShapeDump.BasicShape.WhiteSpace);     // shape.print(-) in a separate class;

		}
		System.out.println("IntegerLiteral " + intValue);
	}
}