package ast.expr_def;

import ast.redesign.ASTNode;

import java.util.List;

/*
    func int main() --> this is a funcBlock which holds the type here 0 in checking we can easily say block.type compare to funcDecl.type { return 0; }
 */
public class FuncBlockExpr extends Expression {
    public List<ASTNode> body;
    public FuncBlockExpr(ExprKind kind) {
        super(ExprKind.FuncBlock);
    }
}
