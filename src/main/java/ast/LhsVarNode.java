package ast;


public class LhsVarNode extends Expression {

	String varName;

	public LhsVarNode(int line, String varName, Type varType) {
		super(line);
		this.varName = varName;
		this.type = varType;
	}


	@Override
	public String toString() {
		return "LhsVarNode{" +
				"varName='" + varName + '\'' +
				", varType=" + type +
				'}';
	}
}
