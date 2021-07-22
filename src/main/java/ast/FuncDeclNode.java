package ast;

import ast.type.Type;

import java.util.ArrayList;

public class FuncDeclNode extends Statement {  // FIXME: Should extends DeclarationNode;


    Type returnType;
    String funcName;
    ArrayList<ParameterNode> funcParams;

    public FuncDeclNode(int line, Type returnType, String funcName, ArrayList<ParameterNode> funcParams) {
        super(line);
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
