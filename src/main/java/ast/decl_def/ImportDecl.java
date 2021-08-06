package ast.decl_def;

import ast.Identifier;

public class ImportDecl extends Decl {
    public Identifier name;

    public ImportDecl(Identifier identifier) {
        super(DeclKind.ImportDecl);
        name = identifier;
    }

}
