package semantic.type_checking;

import ast.ASTInfo;
import ast.decl_def.TranslationUnit;
import ast.decl_def.Decl;
import ast.type.Type;

public abstract class TypeChecker {

    public TranslationUnit translationUnit;
    public ASTInfo astInfo;

    public TypeChecker(TranslationUnit translationUnit, ASTInfo astInfo) {
        this.translationUnit = translationUnit;
        this.astInfo = astInfo;
    }
    public abstract boolean validateType(Decl decl); // validate decl types // Do we have Foo? bool? double? so fourth!
    public abstract boolean validateType(Type type); // validate decl types //
    public abstract void typeCheckDecl(Decl decl); //
}
