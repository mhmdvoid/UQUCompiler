package ast.nodes.expression;

import ast.nodes.ASTNode;
import ast.nodes.visitor.ExprVisitor;
import ast.nodes.visitor.ExprWalker;
import ast.nodes.visitor.FunctionalWalker;
import ast.type.Type;

import java.util.Optional;

public abstract class Expr {

    ExprKind kind;   // This tells us subclasses . It's a design pattern used for polymorphism rather Switch statement.
    public Type type;
    public Expr(ExprKind kind) {
        this.kind = kind;
    }
    public abstract void dump(int indent);
    public abstract Expr accept(ExprVisitor visitor);

    // The interface for other services to walk the expr & subExpr. So other clients won't need to do : subExpr.accept(Visitor visitor);
    public Expr walk(FunctionalWalker function, Object data) {
        return new ExprWalker(function, data).doIt(this);
    }
}
