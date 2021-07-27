package semantic;

import ast.type.Type;
import ast.type.TypeAliasKind;

public class MethodContext extends LocalContext {
    private Type methodReturnType;
    private boolean returnStatementExist = false;
    public MethodContext(Context surroundingContext, Type type) {
        super(surroundingContext);  // if we have OOP surr should be classDecl
        this.methodReturnType = type;
        this.offset = 0;
        // For now method has surrContext point translation as well as this.translation
    }
    public void resolveReturnType() {
        // we need to look it up?
        if (methodReturnType instanceof TypeAliasKind) {
            var tp = (TypeAliasKind) methodReturnType;
            methodReturnType = tp.underlay;
        }
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
