package ast;

import ast.type.Type;
import compile.utils.ShapeDump;
import semantic.Context;
import semantic.Definition;

// For simplicity all vars are initialized for now as grammar rule by parser
// Fixme: this should be called GlobalVarDecl as they have non-trivial rules, Local vars don't have.
public class VarDecl extends Expression {

    String name;



    //    boolean isInitialized;
    Expression initialExpression;

    public VarDecl(int line, String name, Type type, Expression initialExpression) {
        super(line);
        this.name = name;
        this.type = type;
        this.initialExpression = initialExpression;
        typeChecker.typeDeclState(this);
//        if (initialExpression == null) isInitialized = false;
    }


    @Override
    public String toString() {
        return "VarDecl{" +
                "name='" + name + '\'' +
                ", initialExpression=" + initialExpression +
                '}';
    }

    @Override
    protected void dump(int indent) {
        for (int i = 0; i < indent; i++) {
            ShapeDump.spaceTreeShape(ShapeDump.BasicShape.WhiteSpace);
        }
        System.out.println("VarDeclNode");
        initialExpression.dump(indent + 2);
    }

    @Override
    public void semaAnalysis(Context context) {
        // Fixme: for rhs expression if was variable ?
        context.addEntry(getLine(), name, new Definition(type));   // new variable declaration which means no lookup needed;
        // Fixme: As everyDecl requires init rhs expression we should separate nodes;
    }

    public Expression getInitialExpression() {
        return initialExpression;
    }
}
