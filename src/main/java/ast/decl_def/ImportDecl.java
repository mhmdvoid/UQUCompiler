package ast.decl_def;

import ast.Identifier;
import compile.utils.IndentationKind;
import compile.utils.Indenter;
import lexer.Position;

public class ImportDecl extends Decl {
    Position loc;  // import kw loc.
    public Identifier name;

    public ImportDecl(Identifier identifier) {
        super(DeclKind.ImportDecl);
        name = identifier;
    }

    @Override
    public void dump(int indent) {
        Indenter.indentWithShape(indent, IndentationKind.WhiteSpace);//
        System.out.println("ImportDecl");
    }
}
