package ast;

public class ParameterNode extends ASTNode {

    String paramName;

    String typeName;
//    private final int dataType;
//    Type paramType; Todo : Implement simple type system


    public ParameterNode(String paramName, String typeName) {
        this.paramName = paramName;
        this.typeName = typeName;
//        dataType = Types.typenameToInt(typeName);
    }

    @Override
    public String toString() {
        return "ParamNode{" +
                "paramName='" + paramName + '\'' +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
