package ast;

public class ParamNode extends ASTNode {

    String paramName;

    String typeName;
//    private final int dataType;
//    Type paramType; Todo : Implement simple type system


    public ParamNode(String paramName, String typeName) {
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
