package semantic.type_checking;

import ast.NameAliasDeclNode;

public class TypeCheckDecl {
    TypeChecker typeChecker;

    public TypeCheckDecl(TypeChecker typeChecker) {
        this.typeChecker = typeChecker;
    }
    public void visitTypeAlias(NameAliasDeclNode nameAliasDeclNode) {
        typeChecker.validateType(nameAliasDeclNode.getUnderlyingType());
    }
}