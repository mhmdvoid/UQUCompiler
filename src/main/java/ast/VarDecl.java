package ast;

import ast.type.BuiltinType;
import ast.type.Type;
import ast.type.TypeAliasKind;
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
        this.type = type;   // todo: lookup the variable type, for now it's coded by the parser to refuses any type name not recognised , i.e int, bool, void.
        this.initialExpression = initialExpression;
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
    public ASTNode semaAnalysis(Context context) {

        if (type instanceof TypeAliasKind) {    // this is very dependent on varDecl on;y
            var def = context.lookup(type.name); // no def.type should be alias that has underlay type?
            if (def != null) {
                type = def.getType();
            } else {
                type= new BuiltinType(BuiltinType.BuiltinContext.VOID_TYPE); // default it;
                System.err.println("Use of non-declared type " + type.name + " line " + getLine());
            }


        }
        context.addEntry(getLine(), name, new Definition(type));   // new variable declaration which means no lookup needed;
        typeChecker.typeDeclState(this);
        return this;

    }

    public Expression getInitialExpression() {
        return initialExpression;
    }
}
