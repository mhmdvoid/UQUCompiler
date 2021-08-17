package ast.nodes.expression;

import ast.nodes.ASTNode;
import ast.nodes.visitor.ExprVisitor;

import java.util.List;
import java.util.Optional;

/*
    func int main() --> this is a funcBlock which holds the type here 0 in checking we can easily say block.type compare to funcDecl.type { return 0; }
 */
public class FuncBlockExpr extends Expr {
    public List<ASTNode> body;
    public FuncBlockExpr(List<ASTNode> body) {
        super(ExprKind.FuncBlock);
    }

    @Override
    public void dump(int indent) {

    }

    @Override
    public Optional<Expr> accept(ExprVisitor visitor) {
        // TODO
        return Optional.empty();
    }
}
