package ast;

import java.util.ArrayList;

public class MethodDeclNode extends ASTNode {


    String returnType;
    String funcName;
    ParameterNode parameters;
//    BlockNode block;
//    private final int dataType;


    public MethodDeclNode(String returnType, String funcName, ParameterNode parameterNode) {
        this.returnType = returnType;
        this.funcName = funcName;
        this.parameters = parameterNode;
//        this.block = block;
//        dataType = Types.typenameToInt(returnType);
    }

    @Override
    public String toString() {
        return "MethodDeclNode{" +
                "returnType='" + returnType + '\'' +
                ", funcName='" + funcName + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
