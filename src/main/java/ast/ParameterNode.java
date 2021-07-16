package ast;

public class ParameterNode extends ASTNode {

    String paramName;

    Type typeName;
//    private final int dataType;
//    Type paramType; Todo : Implement simple type system


    public ParameterNode(int line, String paramName, Type typeName) {
        super(line);
        this.paramName = paramName;
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "ParamNode{" +
                "paramName='" + paramName + '\'' +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
