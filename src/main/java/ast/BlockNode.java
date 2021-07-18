package ast;


import java.util.List;

public class BlockNode extends ScopeNode {
    List<Statement> statements;

    /*LocalContext lContext;*/
    public BlockNode(int line) {
        super(line);
    }
}
