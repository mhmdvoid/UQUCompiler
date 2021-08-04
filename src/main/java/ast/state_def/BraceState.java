package ast.state_def;

/**
 * {var x: int = 10; print(x); }
 */
public class BraceState extends Statement {
    // TODO
    public BraceState() {
        super(StateKind.BRACE_STATE);
    }
}
