package ast.redesign.decl_def;

import ast.redesign.ASTInfo;

import java.util.ArrayList;
import java.util.List;

public class TranslationUnit {
    ASTInfo astInfo;
    // Loc startOfFile;
    private final List<Decl> decls;

    public TranslationUnit(ASTInfo astInfo) {
        this.astInfo = astInfo;
        decls = new ArrayList<>();
    }

    public boolean push(Decl decl) {
        return decls.add(decl);
    }

    public List<Decl> getDecls() {
        return decls;
    }
}
