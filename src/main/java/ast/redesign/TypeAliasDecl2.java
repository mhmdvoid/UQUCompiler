package ast.redesign;

import ast.type.Type;

// This is the SemaDecl responsibility to create Node , resolve, lookup and search and much much more !;
public class TypeAliasDecl2 {

    Type underlyingType;
    String aliasName;

    public TypeAliasDecl2(Type underlyingType, String aliasName) {
        this.underlyingType = underlyingType;
        this.aliasName = aliasName;
    }
    // if #debug

    @Override
    public String toString() {
        return "TypeAliasDecl2{" +
                "underlyingType=" + underlyingType +
                ", aliasName='" + aliasName + '\'' +
                '}';
    }

    public Type getUnderlyingType() {
        return underlyingType;  // Should return sugared insted
    }
}