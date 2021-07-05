package lang_tokinezer;

public class TypedLanguage extends Language {  // class, private, public, and other;


    enum TypedLangToken implements LangToken {
        PUBLIC,
        PRIVATE,
        CLASS,
    }


    public TypedLanguage() {

        keywords.put("class", TypedLangToken.CLASS);
        keywords.put("public", TypedLangToken.PUBLIC);
        keywords.put("private", TypedLangToken.PRIVATE);
    }
}
