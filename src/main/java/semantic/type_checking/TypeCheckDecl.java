package semantic.type_checking;

import ast.nodes.declaration.TypeAliasDecl;

public class TypeCheckDecl {
    TypeChecker typeChecker;

    public TypeCheckDecl(TypeChecker typeChecker) {
        this.typeChecker = typeChecker;
    }
    public void visitTypeAlias(TypeAliasDecl typeAliasDecl) {
//        typeChecker.validateType(nameAliasDeclNode.getUnderlyingType());
    }
}