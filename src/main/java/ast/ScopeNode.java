package ast;

public class ScopeNode extends Statement {
    /*Polymorphism ScopeContext Subclass: Global & Local*/
    protected ScopeNode(int line) {
        super(line);
    }
}
