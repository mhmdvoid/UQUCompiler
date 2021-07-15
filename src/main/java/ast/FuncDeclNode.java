package ast;

import java.util.ArrayList;

public class FuncDeclNode extends ASTNode {


    Type returnType;
    String funcName;
    ArrayList<ParameterNode> funcParams;

    public FuncDeclNode(Type returnType, String funcName, ArrayList<ParameterNode> funcParams) {
        this.returnType = returnType;
        this.funcName = funcName;
        this.funcParams = funcParams;
    }

    @Override
    public String toString() {
        return "FuncDeclNode{" +
                "returnType='" + returnType + '\'' +
                ", funcName='" + funcName + '\'' +
                ", funcParams=" + funcParams +
                '}';
    }
}
