package ast;

import ast.type.Type;
import semantic.Context;

import java.util.ArrayList;

public class FuncDeclNode extends Statement {  // FIXME: Should extends DeclarationNode;


    Type returnType;
    String funcName;
    ArrayList<ParameterNode> funcParams;
    BlockNode funcBlock;

    public FuncDeclNode(int line, Type returnType, String funcName, ArrayList<ParameterNode> funcParams, BlockNode funcBlock) {
        super(line);
        this.returnType = returnType;
        this.funcName = funcName;
        this.funcParams = funcParams;
        this.funcBlock = funcBlock;
    }

    @Override
    public void semaAnalysis(Context context) {
        // we do the following .. lookup the func return type, as well as params type.
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
