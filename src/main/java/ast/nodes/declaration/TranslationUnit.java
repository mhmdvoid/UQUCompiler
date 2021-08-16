package ast.nodes.declaration;

import ast.nodes.ASTInfo;
import semantic.scope.Scope;

import java.util.ArrayList;
import java.util.List;

public class TranslationUnit {
    public ASTInfo astInfo;
    // Loc startOfFile;
    private final List<Decl> decls;

    public Scope tuScope;
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

    private void dump(int indent) {
        System.out.println("TranslationUnit");
        for (Decl decl : decls)
            decl.dump(indent + 2);
    }
    public void dump() {
        dump(0);
    }
}
