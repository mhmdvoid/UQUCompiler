package ast.decl_def;

import ast.Identifier;
import lexer.Position;

public class ImportDecl extends Decl {
    Position loc;  // import kw loc.
    public Identifier name;

    public ImportDecl(Identifier identifier) {
        super(DeclKind.ImportDecl);
        name = identifier;
    }

}
