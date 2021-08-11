package semantic.type_checking;

import ast.decl_def.TranslationUnit;
import ast.ASTInfo;
import ast.decl_def.Decl;
import ast.type.Type;

public class TypeCheckType extends TypeChecker {

    public TypeCheckType(TranslationUnit translationUnit, ASTInfo astInfo) {
        super(translationUnit, astInfo);
    }

    @Override
    public boolean validateType(Decl decl) {
        return false;
    }

    // false if valid, true if is invalid.
    @Override
    public boolean validateType(Type type) {
        // Todo: Bool, Float, and more builtin type
        switch (type.getKind()) {
            case BUILTIN_32INT_KIND -> { return false; }
//            case TYPEALIAS_KIND -> {
//                // should make a recursive call, if were get the sugared version of alias. fixme
//
//            }
        }
        return true;
    }

    @Override
    public void typeCheckDecl(Decl decl) {

    }
}
