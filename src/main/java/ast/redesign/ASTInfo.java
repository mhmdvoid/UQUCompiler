package ast.redesign;

import ast.type.*;
import ast.type.Builtin;
import ast.type.UnresolvedType;

import java.util.HashSet;
import java.util.Set;

/**
 * This class acts like a factory for all AST nodes.
 */
// There should be allocate method to allocate all ast nodes. - See ContextObject pattern.
public class ASTInfo {
    // id table;
    public Type int32Type;
    public Type unresolvedType;
    public Type bool8Type;

    Set<String> table;

    public ASTInfo() {
        int32Type = new Builtin(TypeKind.BUILTIN_32INT_KIND, "int");
        unresolvedType = new UnresolvedType(TypeKind.UNRESOLVED_KIND, ""); // Later.
        bool8Type = new Builtin(TypeKind.BOOL_8_KIND, "bool");
        table = new HashSet<>();
    }

    public Identifier getIdentifier(String idName) {
        return new Identifier(idName);
    }
}