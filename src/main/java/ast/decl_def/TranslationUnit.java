package ast.decl_def;

import ast.ASTInfo;

import java.util.ArrayList;
import java.util.List;

public class TranslationUnit {
    public ASTInfo astInfo;
    // Loc startOfFile;
    private final List<Decl> decls;

    public List<TypeAliasDecl> unresolvedTypeList;
    public TranslationUnit(ASTInfo astInfo) {
        this.astInfo = astInfo;
        decls = new ArrayList<>();
        unresolvedTypeList = new ArrayList<>();
    }

    public boolean push(Decl decl) {
        return decls.add(decl);
    }

    public List<Decl> getDecls() {
        return decls;
    }
}
