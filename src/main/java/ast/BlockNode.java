package ast;

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
}
