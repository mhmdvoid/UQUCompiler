package ast;

import ast.type.Type;
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
    public void semaAnalysis(Context context) {      // like the surronding ?
        // context is methodContext;

        context.addEntry(getLine(), paramName, new LocalScopeDefinition(type, ((MethodContext) context).offset()));
    }

    @Override
    public String toString() {
        return "ParamNode{" +
                "paramName='" + paramName + '\'' +
                ", typeName='" + type + '\'' +
                '}';
    }
}
