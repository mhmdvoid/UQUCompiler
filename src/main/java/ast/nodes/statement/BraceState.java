package ast.nodes.statement;

/**
 * {var x: int = 10; print(x); }
 */
public class BraceState extends Statement {
    // TODO
    public BraceState() {
        super(StateKind.BRACE_STATE);
    }
}
