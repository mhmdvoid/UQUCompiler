package ast.nodes.declaration;

// TODO
// There should be DeclContext acts like an environment for Decl clients;
// Give them info, Services, stuff if needed by DeclNode subclasses

import ast.nodes.ASTNode;

/**
 *  Class defines all DeclNodes subclasses - Variable, Function, Similar.
 * This should allow us to walk different nodes easily.
 */
public abstract class Decl implements ASTNode {
    DeclKind kind;

    public Decl(DeclKind kind) {
        this.kind = kind;
    }

    public abstract void dump(int indent);
}
