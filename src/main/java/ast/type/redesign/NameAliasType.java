package ast.type.redesign;

import ast.redesign.Identifier;
import ast.type.Type;
import ast.type.TypeKind;

public class NameAliasType extends Type {
    public final NameAliasDeclNode aliasDeclNode;
    public NameAliasType(NameAliasDeclNode aliasDecl) {
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

// all nodes should be constructed by semaDecl/Express invoked by parser;
class NameAliasDeclNode {
    NameAliasType nameAliasType;
    Identifier identifier;
    Type underlyingType;

    public NameAliasDeclNode(Identifier identifier, Type underlyingType) {
        this.underlyingType = underlyingType;
        this.identifier = identifier;
    }

    public NameAliasType getNameAliasType() {
        return (nameAliasType == null) ? nameAliasType = new NameAliasType(this) : nameAliasType; // Todo: We should have Singleton/Context design creation.
    }
    // So in typeObjects we store the node that holds types. Why? In type checking we're no longer interested in ast nodes. Rather we walk the types objects and check their type/underlying type!;

}