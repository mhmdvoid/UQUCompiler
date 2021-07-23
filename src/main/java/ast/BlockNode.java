package ast;

import compile.utils.ShapeDump;
import semantic.Context;
import semantic.LocalContext;

import java.util.List;

//  This is what comes in {..}. Also defines a new scope for local statement , ie. local vars and similar;
public class BlockNode extends ScopeNode {
    List<Statement> statements;

    LocalContext lContext;

    public BlockNode(int line, List<Statement> statements) {
        super(line);
        this.statements = statements;
    }

    @Override
    public void semaAnalysis(Context context) {
        lContext = new LocalContext(context);   // point to methodContext, if another nested local {..} will point to yet another local-scope recursively.
//        for (int i = 0; i < statements.size(); i++) {
////            statements.set(i, statements.get(i).semaAnalysis(lContext));
//
//        }
        statements.forEach(statement -> statement.semaAnalysis(lContext));
    }

    @Override
    protected void dump(int indent) {
        for (int i = 0; i < indent; i++) {
            ShapeDump.spaceTreeShape(ShapeDump.BasicShape.WhiteSpace);
        }
        System.out.println("FuncBlock");
        statements.forEach(statement -> statement.dump(indent + 2));
    }
}
