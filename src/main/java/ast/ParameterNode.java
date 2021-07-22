package ast;

import ast.type.Type;

public class ParameterNode extends ASTNode {

    String paramName;

    Type type;
//    private final int dataType;
//    Type paramType; Todo : Implement simple type system


    public ParameterNode(int line, String paramName, Type type) {
        super(line);
        this.paramName = paramName;
        this.type = type;
    }

    @Override
    public String toString() {
        return "ParamNode{" +
                "paramName='" + paramName + '\'' +
                ", typeName='" + type + '\'' +
                '}';
    }
}
