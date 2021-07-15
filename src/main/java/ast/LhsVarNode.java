package ast;


public class LhsVarNode extends Expression {

	String varName;

	public LhsVarNode(String varName, Type varType) {
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
