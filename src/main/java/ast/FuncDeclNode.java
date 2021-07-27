package ast;

import ast.type.BuiltinType;
import ast.type.Type;
import ast.type.TypeAliasKind;
import compile.utils.ShapeDump;
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
    public ASTNode semaAnalysis(Context context) { // TODO: 7/23/21 Return newNode with context
        if (returnType instanceof TypeAliasKind) {
            // look it up ! it even doesn't exist !
            var def = context.lookup(returnType.name);
           if (def != null) {
               returnType = def.getType();
               System.out.println(returnType);
           } else {
               System.err.println("Use of undeclared Type-alias " + returnType.name);
               returnType = new BuiltinType(BuiltinType.BuiltinContext.VOID_TYPE);
               semaError = true;
           }
        }
        methodContext = new MethodContext(context, returnType);
        context.addEntry(getLine(), funcName, new Definition(returnType), this);    // example of our table [main: int] used by return statement to check
        funcParams.forEach(parameterNode -> {parameterNode.semaAnalysis(methodContext);});
        funcBlock.semaAnalysis(this.methodContext);   // for one block surronding will be methodContext!
        return this;
    }
    @Override
    protected void dump(int indent) {
        for (int i = 0; i < indent; i++) {
            ShapeDump.spaceTreeShape(ShapeDump.BasicShape.WhiteSpace);
        }
        System.out.println("FuncDecl");
        funcParams.forEach(parameterNode -> parameterNode.dump(indent + 2));
        funcBlock.dump(indent + 4);

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
