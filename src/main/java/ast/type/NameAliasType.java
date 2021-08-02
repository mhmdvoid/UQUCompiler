package ast.type;

import ast.redesign.Identifier;
import ast.redesign.decl_def.TypeAliasDecl;
import ast.type.Type;
import ast.type.TypeKind;

public class NameAliasType extends Type {
    public final TypeAliasDecl aliasDeclNode;
    public NameAliasType(TypeAliasDecl aliasDecl) {
        super(TypeKind.TYPEALIAS_KIND, "");
        aliasDeclNode = aliasDecl;
    }

    @Override
    public boolean equalType(Type type) {
        return false;
    }

    @Override
    public boolean binAccept() {
        return false;
    }
    public Type type() {
        return aliasDeclNode.underlyingType;
    }
}