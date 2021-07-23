package ast;

import ast.type.Type;
import compile.utils.ShapeDump;
import semantic.Context;
import semantic.Definition;

public class TypeAliasDecl extends Statement {
    String thaAliasName;
    Type underlayType;

    public TypeAliasDecl(int line, String thaAliasName, Type underlayType) {
        super(line);
        this.thaAliasName = thaAliasName;
        this.underlayType = underlayType;
    }


    @Override
    public void semaAnalysis(Context context) {
        // context is global here;
        context.addEntry(getLine(), thaAliasName, new Definition(underlayType));   // Again: hard-coded types no need to look them up. Fix if user-defined allowed
//        we should return  age analyzing
    }

    @Override
    protected void dump(int indent) {
        for (int i = 0; i < indent; i++) {
            ShapeDump.spaceTreeShape(ShapeDump.BasicShape.WhiteSpace);
        }
        System.out.println("TypeAliasDecl of " + underlayType);
    }
}
