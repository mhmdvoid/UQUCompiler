package ast.nodes.expression;

import ast.nodes.ASTNode;
import ast.nodes.visitor.ExprVisitor;
import java.util.List;
import java.util.Optional;

/// { var x : int = 1; x = 1; if (cond) {} return 0; }
// BlockExpr -
// { BlockElement* }
// BlockElement -
// Decl, Statement, Expr.
// Nodes are way to store visiting info permanently, So that we can handle all compilation stuff later.
public class BlockExpr extends Expr {
    public List<ASTNode> elements; // if back elements isn't return and funcType != void { Sema type Error Analyze. I really love data structure more and more}
    // i am gonna leave to typechecking later.
    public BlockExpr(List<ASTNode> elements) {
        super(ExprKind.FuncBlock);
        this.elements = elements;
    }

    @Override
    public void dump(int indent) {

    }

    public int numElems() { return elements.size(); }

    @Override
    public Optional<Expr> accept(ExprVisitor visitor) {
        // TODO
        return Optional.empty();
    }
}
