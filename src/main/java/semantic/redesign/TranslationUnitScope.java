package semantic.redesign;

public class TranslationUnitScope extends Scope {
    public TranslationUnitScope() {
        super(null, null);
        this.translationUnitScope = this;
    }
}
