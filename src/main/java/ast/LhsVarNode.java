package ast;


public class LhsVarNode extends Expression {

	String varName;
	Type varType;

	public LhsVarNode(String varName, Type varType) {
		this.varName = varName;
		this.varType = varType;
	}

}
