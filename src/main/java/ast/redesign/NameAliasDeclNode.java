package ast.redesign;

import ast.Statement;
import ast.type.Type;

// This is the SemaDecl responsibility to create Node , resolve, lookup and search and much much more !;
public class NameAliasDeclNode extends Statement {

    public Type underlyingType;
    String aliasName;

    public NameAliasDeclNode(Type underlyingType, String aliasName) {
        super(1);
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