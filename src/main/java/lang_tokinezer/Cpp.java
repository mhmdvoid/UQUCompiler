package lang_tokinezer;

public class Cpp extends TypedLanguage {

    enum CppToken implements  LangToken {
        NAMESPACE,
    }

    public Cpp() {
        keywords.put("namespace", CppToken.NAMESPACE);
    }
}
