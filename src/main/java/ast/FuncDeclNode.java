package ast;

import ast.type.Type;
import semantic.Context;
import semantic.Definition;
import semantic.MethodContext;

import java.util.ArrayList;

public class FuncDeclNode extends Statement {  // FIXME: Should extends DeclarationNode;


    Type returnType;
    String funcName;
    ArrayList<ParameterNode> funcParams;
    BlockNode funcBlock;
    MethodContext methodContext;

    public FuncDeclNode(int line, Type returnType, String funcName, ArrayList<ParameterNode> funcParams, BlockNode funcBlock) {
        super(line);
        this.returnType = returnType;
        this.funcName = funcName;
        this.funcParams = funcParams;
        this.funcBlock = funcBlock;
    }

    @Override
    public void semaAnalysis(Context context) {
        methodContext = new MethodContext(context, returnType);
        methodContext.addEntry(getLine(), funcName, new Definition(returnType));    // example of our table [main: int] used by return statement to check

        funcParams.forEach(parameterNode -> {parameterNode.semaAnalysis(methodContext);});
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
