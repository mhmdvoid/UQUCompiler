package ast.redesign;

import ast.type.*;
import ast.type.redesign.Builtin;
import ast.type.redesign.UnresolvedType;

import java.util.HashSet;
import java.util.Set;

/**
 * This class acts like a factory for all AST nodes.
 */
// There should be allocate method to allocate all ast nodes.
public class ASTOwn {
    // id table;
    public Type int32Type;
    public Type unresolvedType;

    Set<String> table;

    public ASTOwn() {
        int32Type = new Builtin(TypeKind.BUILTIN_32INT_KIND, "int");
        unresolvedType = new UnresolvedType(TypeKind.UNRESOLVED_KIND, ""); // Later.
        table = new HashSet<>();
    }

    public Identifier getIdentifier(String idName) {
        return new Identifier(idName);
    }
}