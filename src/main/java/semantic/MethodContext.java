package semantic;

import ast.type.Type;

public class MethodContext extends LocalContext {
    private Type methodReturnType;
    private boolean returnStatementExist = false;
    public MethodContext(Context surroundingContext, Type type) {
        super(surroundingContext);  // if we have OOP surr should be classDecl
        this.methodReturnType = type;
        this.offset = 0;
        // For now method has surrContext point translation as well as this.translation
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
