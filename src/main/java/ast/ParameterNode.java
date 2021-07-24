package ast;

import ast.type.BuiltinType;
import ast.type.Type;
import ast.type.TypeAliasKind;
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
        if (type instanceof TypeAliasKind) {    // this is very dependent on varDecl on;y
            var def = context.lookup(type.name); // no def.type should be alias that has underlay type?
            if (def != null) {
                type = def.getType();
            } else {
                semaError = true;  // Todo: There should be probably sorta diagnostic-engine tool
                type= new BuiltinType(BuiltinType.BuiltinContext.VOID_TYPE); // default it;
                System.err.println("Use of non-declared type " + type.name + " line " + getLine());
            }
        }
        context.addEntry(getLine(), paramName, new LocalScopeDefinition(type, ((MethodContext) context).offset()), this);
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
