package ast;

import java.util.ArrayList;

public class MethodDeclNode extends ASTNode {


    String returnType;
    String funcName;
    ArrayList<ParameterNode> parameters;
    BlockNode block;


    public MethodDeclNode(String returnType, String funcName, ArrayList<ParameterNode> parameters, BlockNode block) {
        this.returnType = returnType;
        this.funcName = funcName;
        this.parameters = parameters;
        this.block = block;
    }
}
