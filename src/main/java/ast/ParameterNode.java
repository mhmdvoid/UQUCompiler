package ast;

import ast.type.Type;
import compile.utils.ShapeDump;
import semantic.Context;
import semantic.LocalScopeDefinition;
import semantic.MethodContext;

public class ParameterNode extends ASTNode {

    String paramName;

    Type type;
//    private final int dataType;
//    Type paramType; Todo : Implement simple type system


    public ParameterNode(int line, String paramName, Type type) {
        super(line);
        this.paramName = paramName;
        this.type = type;
    }

    @Override
    public ASTNode semaAnalysis(Context context) {      // like the surronding ?
        // context is methodContext;
        System.out.println(type);   // it;s not looked up !;
        context.addEntry(getLine(), paramName, new LocalScopeDefinition(type, ((MethodContext) context).offset()));
        return this;
    }

    @Override
    protected void dump(int indent) {
        for (int i = 0; i < indent; i++) {
            ShapeDump.spaceTreeShape(ShapeDump.BasicShape.WhiteSpace);
        }
        System.out.println("Parameter " + paramName);
    }

    @Override
    public String toString() {
        return "ParamNode{" +
                "paramName='" + paramName + '\'' +
                ", typeName='" + type + '\'' +
                '}';
    }
}
