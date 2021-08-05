package ast.decl_def;

import ast.Identifier;

public class ImportDecl extends Decl {
    Identifier name;

    public ImportDecl(Identifier identifier) {
        super(DeclKind.ImportDecl);
        name = identifier;
    }

}
