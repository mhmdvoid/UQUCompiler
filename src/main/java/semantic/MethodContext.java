package semantic;

import ast.type.Type;

public class MethodContext extends LocalContext {
    private Type methodReturnType;
    private boolean returnStatementExist = false;
    public MethodContext(Context surroundingContext, Type type) {
        super(surroundingContext);
        this.methodReturnType = type;
        this.offset = 0;
    }

    public void returnStatementDoesExist() {
        returnStatementExist = true;
    }

    public Type getMethodReturnType() {
        return methodReturnType;
    }

    public boolean isReturnStatementExist() {
        return returnStatementExist;
    }
}
