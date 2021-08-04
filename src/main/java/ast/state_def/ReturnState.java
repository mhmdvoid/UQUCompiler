package ast.state_def;

public class ReturnState extends Statement {
    public ReturnState( ) {
        super(StateKind.RETURN_STATE);
    }
}

// So to be clear Expression something comes to = rhs;